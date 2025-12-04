package com.stockmanager.stockmanager_be.constant;

public class ApiUriConstants {

    private ApiUriConstants(){
        throw new IllegalStateException("Illegal instantiate");
    }

    // Authentication endpoints
    public static final String AUTH_SIGN_UP = "/register";
    public static final String AUTH_LOGIN = "/login";

    // Category endpoints
    public static final String CATEGORY_ADD = "/add_category";
    public static final String CATEGORY_GET = "/{id}";
    public static final String CATEGORY_GET_ALL = "/get_all";
    public static final String CATEGORY_DELETE = "/delete/{id}";

    public static final String PRODUCT_ADD = "/add_product";
    public static final String PRODUCT_LIST_ADD = "/add_products_list";
    public static final String PRODUCT_GET = "/{productId}";
    public static final String PRODUCTS_GET_ALL = "/get_all";
    public static final String PRODUCT_LOW_STOCK = "/low_stock";
    public static final String PRODUCTS_CATEGORY = "/product_category_info";
    public static final String PRODUCTS_PAGINATED = "/paginated";
    public static final String PRODUCT_UPDATE = "/update_product/{productId}";
    public static final String PRODUCT_DELETE = "/delete_product/{productId}";

}
