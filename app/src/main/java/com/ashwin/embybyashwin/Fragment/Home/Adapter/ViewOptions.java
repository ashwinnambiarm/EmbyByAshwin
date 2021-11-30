package com.ashwin.embybyashwin.Fragment.Home.Adapter;

import mediabrowser.model.drawing.ImageFormat;
import mediabrowser.model.entities.ImageType;

public class ViewOptions {
    private boolean showProgressBar;
    private ImageFormat imageFormat = ImageFormat.Png;
    private ImageType imageType = ImageType.Primary;
    private Integer maxWidth = 280;

    public boolean isShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public ImageFormat getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(ImageFormat imageFormat) {
        this.imageFormat = imageFormat;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }
}
