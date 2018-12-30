package com.master.ordercoffee.utils;

public class Constants {

    public static String PROJECT_ID = "ordercoffee-f070f";

    public static class UserType {
        public static int STAFF = 1;
        public static int CHEF = 2;
        public static int ADMIN = 4;
    }

    public static class BookingStatus {
        public static int BOOK = 10;
        public static int PROGRESS = 20;
        public static int DONE = 30;
        public static int PAIED = 40;
    }

    public static class Keys {
        public static String kPathImageUpload = "kPathImageUpload";
        public static String kCaptureImageType = "kCaptureImageType";
        public static String kImageUrl = "kImageUrl";
    }

    public static class Requests {
        public static final int REQUEST_SEARCH_PLACE = 1000;
        public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1002;
        public static final int REQUEST_CAPTURE_IMAGE = 555;
        public static final int REQUEST_FACEBOOK_LOGIN = 561;
        public static final int REQUEST_GOOGLE_LOGIN = 562;
        public static final int REQUEST_DETAILPROMOITION = 1995;
        public static final int REQUEST_UPDATE_PROFILE = 572;
        public static final int REQUEST_PROFILE_DRIVER = 573;
        public static final int REQUEST_PICK_CONTACTS = 574;
        public static final int REQUEST_EDIT_EMAIL = 575;
        public static final int REQUEST_IMAGE_FOR_AVATAR = 576;
        public static final int REQUEST_ADD_CLIENT = 758;
        public static final int REQUEST_PERMISSION_CAMERA = 577;
        public static final int REQUEST_PERMISSION_WRITE = 578;
        public static final int REQUEST_TRANSFER_CASH = 579;
        public static final int REQUEST_CHOOSE_IMAGE_CAR = 580;
        public static final int REQUEST_INVITE = 581;
    }

}
