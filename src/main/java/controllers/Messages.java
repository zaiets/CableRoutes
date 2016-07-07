package controllers;

public enum Messages {

    WORK_DONE_OK("working... success!"),
    //DB init
    JP_INIT_ERROR("can't read join points from file"),
    EQUIP_INIT_ERROR ("can't read equipments from file"),
    ROUTES_INIT_ERROR ("can't read routes from file"),
    JOURNALS_INIT_ERROR ("can't read journals from files"),

    //Input file(s)
    JP_INPUT ("choose join points data file"),
    EQUIP_INPUT ("choose equipments data file"),
    ROUTES_INPUT ("choose routes data file"),
    JOURNALS_INPUT ("choose journal files"),
    PATH_INPUT ("choose output path to work with!"),

    //defaults
    MESSAGE_SMTHN_WRONG ("При обработке произошла ошибка!"),
    MESSAGE_READY ("...result will be shown here"),
    CHOOSE_FILE("...choose file"),
    CHOOSE_TARGET_PATH ("...choose target path"),
    CHOSEN_DEFAULT_FILE ("chosen: default path or file"),

    //tracer
    TRACING_ERRORS ("Can't trace model"),
    //calc
    CALC_ERRORS ("Can't calculate model");


    private String message;

    Messages (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
