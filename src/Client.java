public class Client {
    private String name;
    private String memberShip;

    public Client(String name, String memberShip) {
        this.name = name;
        this.memberShip = memberShip;
    }
    public String getMembership(){
        return memberShip;
    }
    public String getName() {
        return name;
    }

}
