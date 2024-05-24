package users.utils;

import java.util.HashMap;

public class FederationEntity {

    private static final HashMap<Country, String> federationEntities = new HashMap<>();
    private static final HashMap<Country, String> countryToString = new HashMap<>();
    private static FederationEntity instance = null;
    
    public FederationEntity(){
        federationEntities.put(Country.Aguascalientes, "AS");
        federationEntities.put(Country.Baja_California, "BC");
        federationEntities.put(Country.Baja_California_Sur, "BS");
        federationEntities.put(Country.Campeche, "CC");
        federationEntities.put(Country.Coahuila, "CL");
        federationEntities.put(Country.Colima, "CM");
        federationEntities.put(Country.Chiapas, "CS");
        federationEntities.put(Country.Chihuahua, "CH");
        federationEntities.put(Country.CDMX, "DF");
        federationEntities.put(Country.Durango, "DG");
        federationEntities.put(Country.Guanajuato, "GT");
        federationEntities.put(Country.Guerrero, "GR");
        federationEntities.put(Country.Hidalgo, "HG");
        federationEntities.put(Country.Jalisco, "JC");
        federationEntities.put(Country.Estado_de_Mexico, "MC");
        federationEntities.put(Country.Michoacan, "MN");
        federationEntities.put(Country.Morelos, "MS");
        federationEntities.put(Country.Nayarit, "NT");
        federationEntities.put(Country.Nuevo_Leon, "NL");
        federationEntities.put(Country.Oaxaca, "OC");
        federationEntities.put(Country.Puebla, "PL");
        federationEntities.put(Country.Queretaro, "QT");
        federationEntities.put(Country.Quintana_Roo, "QR");
        federationEntities.put(Country.San_Luis_Potosi, "SP");
        federationEntities.put(Country.Sinaloa, "SL");
        federationEntities.put(Country.Sonora, "SR");
        federationEntities.put(Country.Tabasco, "TC");
        federationEntities.put(Country.Tamaulipas, "TS");
        federationEntities.put(Country.Tlaxcala, "TL");
        federationEntities.put(Country.Veracruz, "VZ");
        federationEntities.put(Country.Yucatan, "YN");
        federationEntities.put(Country.Zacatecas, "ZS");
        federationEntities.put(Country.Nacido_En_Extranjero, "NE");
        
        countryToString.put(Country.Aguascalientes, "AGUASCALIENTES");
        countryToString.put(Country.Baja_California, "BAJA CALIFORNIA");
        countryToString.put(Country.Baja_California_Sur, "BAJA CALIFORNIA SUR");
        countryToString.put(Country.Campeche, "CAMPECHE");
        countryToString.put(Country.Coahuila, "COAHUILA");
        countryToString.put(Country.Colima, "COLIMA");
        countryToString.put(Country.Chiapas, "CHIAPAS");
        countryToString.put(Country.Chihuahua, "CHIHUAHUA");
        countryToString.put(Country.CDMX, "CIUDAD DE MÉXICO");
        countryToString.put(Country.Durango, "DURANGO");
        countryToString.put(Country.Guanajuato, "GUANAJUATO");
        countryToString.put(Country.Guerrero, "GUERRERO");
        countryToString.put(Country.Hidalgo, "HIDALGO");
        countryToString.put(Country.Jalisco, "JALISCO");
        countryToString.put(Country.Estado_de_Mexico, "ESTADO DE MÉXICO");
        countryToString.put(Country.Michoacan, "MICHOACÁN");
        countryToString.put(Country.Morelos, "MORELOS");
        countryToString.put(Country.Nayarit, "NAYARIT");
        countryToString.put(Country.Nuevo_Leon, "NUEVO LEÓN");
        countryToString.put(Country.Oaxaca, "OAXACA");
        countryToString.put(Country.Puebla, "PUEBLA");
        countryToString.put(Country.Queretaro, "QUERÉTARO");
        countryToString.put(Country.Quintana_Roo, "QUINTANA ROO");
        countryToString.put(Country.San_Luis_Potosi, "SAN LUIS POTOSÍ");
        countryToString.put(Country.Sinaloa, "SINALOA");
        countryToString.put(Country.Sonora, "SONORA");
        countryToString.put(Country.Tabasco, "TABASCO");
        countryToString.put(Country.Tamaulipas, "TAMAULIPAS");
        countryToString.put(Country.Tlaxcala, "TLAXCALA");
        countryToString.put(Country.Veracruz, "VERACRUZ");
        countryToString.put(Country.Yucatan, "YUCATÁN");
        countryToString.put(Country.Zacatecas, "ZACATECAS");
        countryToString.put(Country.Nacido_En_Extranjero, "NACIDO EN EXTRANJERO");
        
    }

    public static String getFederationEntity(Country country){
        return FederationEntity.getInstance().getFederationEntities().get(country);
    }

    public static String getStringCountry(Country country){
        return FederationEntity.getInstance().getStringsCountry().get(country);
    }

    private static FederationEntity getInstance(){
        if (instance == null) {
            instance = new FederationEntity();
        }
        return instance;
    }

    private HashMap<Country, String> getFederationEntities(){
        return federationEntities;
    }

    private HashMap<Country, String> getStringsCountry(){
        return countryToString;
    }

}
