package com.cedrickchee.pytorchmobilekit;

import android.content.Intent;
import android.os.Bundle;

import com.cedrickchee.pytorchmobilekit.R;
import com.cedrickchee.pytorchmobilekit.vision.ImageClassificationActivity;

public class MainActivity extends AbstractListActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    findViewById(R.id.vision_card_resnext_click_area).setOnClickListener(v -> {
      final Intent intent = new Intent(MainActivity.this, ImageClassificationActivity.class);
      intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "resnext50_32x4d.pt");
      intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
              InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_RESNEXT);
      startActivity(intent);
    });

    findViewById(R.id.vision_card_qmobilenet_click_area).setOnClickListener(v -> {
      final Intent intent = new Intent(MainActivity.this, ImageClassificationActivity.class);
      intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
      intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
              InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
      startActivity(intent);
    });

    findViewById(R.id.vision_card_resnet_click_area).setOnClickListener(v -> {
      final Intent intent = new Intent(MainActivity.this, ImageClassificationActivity.class);
      intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "resnet18.pt");
      intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
              InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_RESNET);
      startActivity(intent);
    });

    findViewById(R.id.vision_card_squeezenet_click_area).setOnClickListener(v -> {
      final Intent intent = new Intent(MainActivity.this, ImageClassificationActivity.class);
      intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "squeezenet1_1.pt");
      intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
              InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_SQUEEZENET);
      startActivity(intent);
    });
  }

  @Override
  protected int getListContentLayoutRes() {
    return R.layout.vision_list_content;
  }
}
