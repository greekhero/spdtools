package ua.org.tumakha.spdtool.enums;

/**
 * @author Yuriy Tumakha
 */
public enum UARegion {

    EMPTY("", ""),
    AR_CRIMEA("АР Крим", "Crimea"),
    VINNYTSIA("Вінницька", "Vinnytsia"),
    VOLYN("Волинська", "Volyn"),
    DNIPROPETROVSK("Дніпропетровська", "Dnipropetrovsk"),
    DONETSK("Донецька", "Donetsk"),
    ZHYTOMYR("Житомирська", "Zhytomyr"),
    TRANSCARPATHIAN("Закарпатська", "Transcarpathian"),
    ZAPORIZHZHIA("Запорізька", "Zaporizhzhia"),
    IVANO_FRANKIVSK("Івано-Франківська", "Ivano-Frankivsk"),
    KYIV("Київська", "Kyiv"),
    KIROVOHRAD("Кіровоградська", "Kirovohrad"),
    LUGANSK("Луганська", "Luhansk"),
    LVIV("Львівська", "Lviv"),
    MYKOLAIV("Миколаївська", "Mykolaiv"),
    ODESSA("Одеська", "Odesa"),
    POLTAVA("Полтавська", "Poltava"),
    RIVNE("Рівненська", "Rivne"),
    SUMY("Сумська", "Sumy"),
    TERNOPIL("Тернопільська", "Ternopil"),
    KHARKIV("Харківська", "Kharkiv"),
    KHERSON("Херсонська", "Kherson"),
    KHMELNYTSKY("Хмельницька", "Khmelnytsky"),
    CHERKASY("Черкаська", "Cherkasy"),
    CHERNIVTSI("Чернівецька", "Chernivtsi"),
    CHERNIHIV("Чернігівська", "Chernihiv");    // Excluded: cities Kyiv, Sevastopol

    private static final String REGION_SUFFIX = " oбл.";
    private static final String REGION_SUFFIX_EN = " region";

    private String name;
    private String nameEn;

    UARegion(String name, String nameEn) {
        this.name = name;
        this.nameEn = nameEn;
    }

    public static UARegion valueOf(String region, String regionEn) {
        for (UARegion uaRegion : values()) {
            if (uaRegion.getName().equals(region) || uaRegion.getNameEn().equals(regionEn)) {
                return uaRegion;
            }
        }
        return EMPTY;
    }

    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getRegion() {
        StringBuilder sb = new StringBuilder(name);
        if (!this.equals(AR_CRIMEA)) {
            sb.append(REGION_SUFFIX);
        }
        return sb.toString();
    }

    public String getRegionEn() {
        StringBuilder sb = new StringBuilder(nameEn);
        if (!this.equals(AR_CRIMEA)) {
            sb.append(REGION_SUFFIX_EN);
        }
        return sb.toString();
    }

    public String getLabel() {
        if (name.length() == 0) {
            return name;
        }
        StringBuilder sb = new StringBuilder(name);
        sb.append(" / ");
        sb.append(nameEn);
        return sb.toString();
    }

}

