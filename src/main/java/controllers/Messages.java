package controllers;

public enum Messages {
    //DB init
    INIT_DONE_OK ("reading... success!"),
    JP_INIT_ERROR("can't read join points from file"),
    EQUIP_INIT_ERROR ("can't read equipments from file"),
    ROUTES_INIT_ERROR ("can't read routes from file"),
    JOURNALS_INIT_ERROR ("can't read journals from files"),

    //Input file(s)
    JP_INPUT ("choose join points data file"),
    EQUIP_INPUT ("choose equipments data file"),
    ROUTES_INPUT ("choose routes data file"),
    JOURNALS_INPUT ("choose journal files"),

    //defaults
    MESSAGE_SMTHN_WRONG ("Ошибка в программе!"),
    CHOOSE_YOUR_FILE ("...choose file"),
    CHOSEN_DEFAULT_FILE ("chosen: default path or file");


    private String message;

    Messages (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
