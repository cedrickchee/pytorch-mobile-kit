# PyTorch Mobile Kit

[PyTorch Mobile](https://pytorch.org/mobile/home/) Kit is a starter kit app that does Machine Learning on edge from camera output, photos, and videos.

**Demo**

Coming soon! Stay tuned...

<!-- TODO: add screencast -->

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
