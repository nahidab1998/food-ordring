package com.example.food_orderig.activity.product.mvp;

public class ProductModel implements ProductPresenter{
    ProductView view;

    public ProductModel(ProductView view) {
        this.view = view;
    }

    @Override
    public void getData() {
        view.setData("bagheri");
    }
}
