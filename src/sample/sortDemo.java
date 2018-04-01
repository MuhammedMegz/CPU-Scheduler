package sample;

import java.util.ArrayList;
import java.util.Collections;

public class sortDemo {
    public static void main(String[] args){
        ArrayList<process> list = new ArrayList<>();
        list.add(new process("p1" ,10 ,0 ));
        list.add(new process("p2" ,2 ,1 ));
        list.add(new process("p3" ,30 ,10 ));
        list.add(new process("p4" ,15 ,20 ));
        list.add(new process("p5" ,25 ,5 ));
        list.add(new process("p6" ,1 ,5 ));
        Collections.sort(list);
    }
}
