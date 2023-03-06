package main.java.classes;

import java.util.Objects;

public class Neighbourhood{
    private int id;
    private String name;
    private String ward;

    public Neighbourhood(){
        this(0,"", "");
    }

    public Neighbourhood(int id, String name, String ward) {
        this.id = id;
        this.name = name;
        this.ward = ward;
    }


    //setters
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setWard(String ward){this.ward = ward;}

    //getters
    public int getId(){return this.id;}

    public String getName() {return this.name;}
    public String getWard() {return this.ward;}

    @Override
    public String toString(){
        if (name.isEmpty() && ward.isEmpty()){
            return "";
        }
        return name + " (" + ward + ")";
    }

    public boolean isEmpty(){
        return name.isEmpty() && ward.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbourhood that = (Neighbourhood) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(ward, that.ward);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ward);
    }

}
