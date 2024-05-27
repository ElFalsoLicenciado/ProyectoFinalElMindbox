import mindbox.utils.CareerType;
import utils.DialogHelper;
import users.utils.Role;

public class Prueba {

    public static void main(String[] args) {

        generateNumCon("Juan","2005",CareerType.ISC,Role.STUDENT);

    }

    public static void generateNumCon(String name, String year, CareerType careerType, Role role){
        String numCon = "";
        switch (role){
            case STUDENT ->  numCon = "I";
            case TEACHER -> numCon = "M";
            case COORDINATOR -> numCon = "C";
        }
        numCon = String.format("%s%s%s%s",numCon,name.substring(0,1).toUpperCase(),year.substring(2,4),careerType.toString());
        DialogHelper.info(numCon);
    }

}

