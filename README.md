# PyTorch Mobile Kit

[PyTorch Mobile](https://pytorch.org/mobile/home/) Kit is a starter kit app that does Machine Learning on edge from camera output, photos, and videos.

**Demo**

![demo](https://raw.githubusercontent.com/cedrickchee/pytorch-mobile-kit/master/docs/demo.gif)

[Watch on YouTube](https://youtu.be/XivOksHKQCk).

## Project Structure

The code for the Android project is in this [_PyTorchMobileKit_ directory](https://github.com/cedrickchee/pytorch-mobile-kit/tree/master/PyTorchMobileKit).

### Machine Learning Models

Currently, the Android app are using pre-trained Computer Vision model, which is packaged in [TorchVision](https://pytorch.org/docs/stable/torchvision/index.html). These models are optimized for offline and low-latency inference on mobile devices:

- [MobileNet v2](https://pytorch.org/docs/stable/torchvision/models.html#torchvision.models.mobilenet_v2)
- [ResNet-18](https://pytorch.org/docs/stable/torchvision/models.html#torchvision.models.resnet18)
- [ResNeXt-50 32x4d](https://pytorch.org/docs/stable/torchvision/models.html#torchvision.models.resnext50_32x4d)
- [SqueezeNet 1.1](https://pytorch.org/docs/stable/torchvision/models.html#torchvision.models.squeezenet1_1)

## Software Requirements

- Python 3.6+
- PyTorch 1.3+

<!--
To get device camera output it uses [Android CameraX API](https://developer.android.com/training/camerax).

All the logic that works with CameraX is separated to [`com.cedrickchee.pytorchmobilekit.vision.AbstractCameraXActivity`](https://github.com/cedrickchee/pytorch-mobile-kit/blob/master/PyTorchMobileKit/app/src/main/java/com/cedrickchee/pytorchmobilekit/vision/AbstractCameraXActivity.java) class.

```
void setupCameraX() {
    final PreviewConfig previewConfig = new PreviewConfig.Builder().build();
    final Preview preview = new Preview(previewConfig);
    preview.setOnPreviewOutputUpdateListener(output -> mTextureView.setSurfaceTexture(output.getSurfaceTexture()));

    final ImageAnalysisConfig imageAnalysisConfig =
        new ImageAnalysisConfig.Builder()
            .setTargetResolution(new Size(224, 224))
            .setCallbackHandler(mBackgroundHandler)
            .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            .build();
    final ImageAnalysis imageAnalysis = new ImageAnalysis(imageAnalysisConfig);
    imageAnalysis.setAnalyzer(
        (image, rotationDegrees) -> {
          analyzeImage(image, rotationDegrees);
        });

    CameraX.bindToLifecycle(this, preview, imageAnalysis);
  }

  void analyzeImage(android.media.Image, int rotationDegrees)
```

Where the `analyzeImage` method process the camera output, `android.media.Image`.

It uses the aforementioned [`TensorImageUtils.imageYUV420CenterCropToFloat32Tensor`](https://github.com/pytorch/pytorch/blob/master/android/pytorch_android_torchvision/src/main/java/org/pytorch/torchvision/TensorImageUtils.java#L90) method to convert `android.media.Image` in `YUV420` format to input tensor.

After getting predicted scores from the model it finds top K classes with the highest scores and shows on the UI.
-->

---

## Get Started

<details>

<summary><b>Expand Get Started on Android</b></summary>

### Tutorial with a Basic Example

[_BasicApp_](https://github.com/cedrickchee/pytorch-mobile-kit/tree/master/BasicApp) is a simple image classification application that demonstrates how to use PyTorch Android API.

This application runs TorchScript serialized TorchVision pretrained Resnet-18 model on static image which is packaged inside the app as Android asset.

### 1. Model Preparation

Let’s start with model preparation. If you are familiar with PyTorch, you probably should already know how to train and save your model. In case you don’t, we are going to use a pre-trained image classification model (Resnet18), which is packaged in [TorchVision](https://pytorch.org/docs/stable/torchvision/index.html).

To install it, run the command below:
```
pip install torchvision
```

To serialize the model you can use Python [scripts](https://github.com/cedrickchee/pytorch-mobile-kit/blob/master/model/resnet18_torchscript_mod.py) in the _model_ directory:

```
import torch
import torchvision

model = torchvision.models.resnet18(pretrained=True)
model.eval()
input = torch.rand(1, 3, 224, 224)
traced_script_module = torch.jit.trace(model, input)
traced_script_module.save("../BasicApp/app/src/main/assets/resnet18.pt")
```

If everything works well, we should have our model - [`resnet18.pt`](https://github.com/cedrickchee/pytorch-mobile-kit/tree/master/BasicApp/app/src/main/assets/resnet18.pt) generated in the assets directory of Android application.

That will be packaged inside Android application as `asset` and can be used on the device.

More details about TorchScript you can find in [tutorials on pytorch.org](https://pytorch.org/docs/stable/jit.html).

### 2. Cloning from GitHub

```
git clone https://github.com/cedrickchee/pytorch-mobile-kit.git
cd BasicApp
```

If [Android SDK](https://developer.android.com/studio/index.html#command-tools) and [Android NDK](https://developer.android.com/ndk/downloads) are already installed you can install this application to the connected android device or emulator with:

```
./gradlew installDebug
```

We recommend you to open this project in [Android Studio 3.5.1+](https://developer.android.com/studio) (At the moment PyTorch Android and demo applications use [Android gradle plugin of version 3.5.0](https://developer.android.com/studio/releases/gradle-plugin#3-5-0), which is supported only by Android Studio version 3.5.1 and higher),
in that case you will be able to install Android NDK and Android SDK using Android Studio UI.

### 3. Gradle Dependencies

Pytorch Android is added to the _BasicApp_ as [gradle dependencies](https://github.com/cedrickchee/pytorch-mobile-kit/blob/master/BasicApp/app/build.gradle#L30-L31) in _build.gradle_:

```
repositories {
    jcenter()
}

dependencies {
    implementation 'org.pytorch:pytorch_android:1.3.0'
    implementation 'org.pytorch:pytorch_android_torchvision:1.3.0'
}
```

where `org.pytorch:pytorch_android` is the main dependency with PyTorch Android API, including libtorch native library for all 4 Android abis (armeabi-v7a, arm64-v8a, x86, x86_64). In this [doc](https://pytorch.org/mobile/android/#building-pytorch-android-from-source), you can find how to rebuild it from source only for specific list of Android abis.

`org.pytorch:pytorch_android_torchvision` - additional library with utility functions for converting `android.media.Image` and `android.graphics.Bitmap` to tensors.

### 4. Loading TorchScript Module
```
Module module = Module.load(assetFilePath(this, "model.pt"));
```
`org.pytorch.Module` represents `torch::jit::script::Module` that can be loaded with `load` method specifying file path to the serialized to file model.

#### 5. Preparing Input

```
Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bitmap,
    TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);
```

`org.pytorch.torchvision.TensorImageUtils` is part of `org.pytorch:pytorch_android_torchvision` library.

The `TensorImageUtils#bitmapToFloat32Tensor` method creates tensors in the [torchvision format](https://pytorch.org/docs/stable/torchvision/models.html) using `android.graphics.Bitmap` as a source.

> All pre-trained models expect input images normalized in the same way, i.e. mini-batches of 3-channel RGB images of shape (3 x H x W), where H and W are expected to be at least 224.
> The images have to be loaded in to a range of `[0, 1]` and then normalized using `mean = [0.485, 0.456, 0.406]` and `std = [0.229, 0.224, 0.225]`

`inputTensor`'s shape is `1x3xHxW`, where `H` and `W` are bitmap height and width appropriately.

### 6. Run Inference

```
Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
float[] scores = outputTensor.getDataAsFloatArray();
```

`org.pytorch.Module.forward` method runs loaded module's `forward` method and gets result as `org.pytorch.Tensor` outputTensor with shape `1x1000`.

### 7. Processing Results

Its content is retrieved using `org.pytorch.Tensor.getDataAsFloatArray()` method that returns Java array of floats with scores for every ImageNet class.

After that we just find index with maximum score and retrieve predicted class name from `Constants.IMAGENET_CLASSES` array that contains all ImageNet classes.

```
float maxScore = -Float.MAX_VALUE;
int maxScoreIdx = -1;
for (int i = 0; i < scores.length; i++) {
  if (scores[i] > maxScore) {
    maxScore = scores[i];
    maxScoreIdx = i;
  }
}
String className = Constants.IMAGENET_CLASSES[maxScoreIdx];
```
</details>

## Background

Previous attempts:

- [PyTorch Lite](https://github.com/cedrickchee/pytorch-lite)
- [PyTorch on Android](https://github.com/cedrickchee/pytorch-android)

#### License

This repository contains a variety of content; some developed by Cedric Chee, and some from third-parties. The third-party content is distributed under the license provided by those parties.

*I am providing code and resources in this repository to you under an open source license.  Because this is my personal repository, the license you receive to my code and resources is from me and not my employer.*

The content developed by Cedric Chee is distributed under the following license:

**Code**

The code in this repository is released under the [MIT license](LICENSE).
