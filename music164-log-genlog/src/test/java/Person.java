public class Person {

    private int id;
    private String name;
    private int age;
    private String[] wife;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(int id, String name, int age, String[] wife) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.wife = wife;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String[] getWife() {
        return wife;
    }

    public void setWife(String[] wife) {
        this.wife = wife;
    }
}
