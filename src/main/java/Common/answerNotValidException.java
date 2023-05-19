package Common;

public class answerNotValidException extends Exception{
    public answerNotValidException(){
        super("La respuesta no es válida");
    }
    public answerNotValidException(String a){
        super("La respuesta "+a+" no es válida, escriba otra. ");
        System.out.print("La respuesta "+a+" no es válida, escriba otra: ");
    }
    public answerNotValidException(int a){
        super("La respuesta "+a+" no es valida");
        System.out.print("La respuesta "+a+" no es valida, porfavor escriba otra: ");
    }
    public answerNotValidException(double a){
        super("La respuesta "+a+" está fuera de rango");
        System.out.print("La respuesta "+a+" está fuera de rango, por favor escriba otra: ");
    }
}
