package Beans;

public enum Category {

    Food("Food",1),
    Electricity("Electricity",2),
    Restaurant("Restaurant",3),
    Vacation("Vacation",4);

    private final String categoryName;
    private final int code;


    Category(String categoryName, int code) {
        this.categoryName = categoryName;
        this.code = code;
    }



    public String getCategoryName() {
        return categoryName;
    }

    public int getCode() {
        return code;
    }


}
