import java.util.*;
import java.io.*;

class Customer{
    public static int id;
    int Executiveid;
    int Customerid;
    char Restaurent;
    char dropping;
    String Time;
    int cost;
    Customer(char Restaurent,char dropping,String Time ){
            this.Restaurent=Restaurent;
            this.dropping=dropping;
            this.Time=Time;
            id++;
            Customerid=id;
    }

}
public class Executive{
        static int id;
        public static int timeCal(String s){
            String st="";
            int t=0;
            for(int i=0; i<s.length(); i++){
                if(s.charAt(i)=='.'){
                    t = Integer.parseInt(st);
                    t *= 60;
                    st="";
                }
                if(s.charAt(i)=='A' || s.charAt(i)=='P'){
                    t+=Integer.parseInt(st);
                }
                if(s.charAt(i)!='.')
                    st += s.charAt(i);
            }
            return (t);
        }
        public static void addCustomer(Customer cus,List<Customer> arr){
            int flag=0;
            for(Customer each:arr){
                if((each.Restaurent==cus.Restaurent)&&(each.dropping==cus.dropping)&& Math.abs(timeCal(each.Time)-timeCal(cus.Time))<=15){
                    flag=1;
                    cus.Executiveid=each.Executiveid;
                    cus.Time=each.Time;
                    cus.cost=5;
                }
            }
            if(flag==0){
                cus.Executiveid=id;
                id++; 
            }
            arr.add(cus);
            
        }

        public static void Printall(List<Customer> arr, int executive){
            int price=0;
            int order=0;
            char des=' ';
            char res=' ';
            String pickup="";
            String drop="";

            for(Customer i:arr){
                    if(i.Executiveid==executive){
                            price+=i.cost;
                            order++;
                            des = i.dropping;
                            res = i.Restaurent;
                            int temp = timeCal(i.Time);
                            temp+=15;
                            pickup=temp/60 + "." + temp%60; 
                            temp += 30;
                            drop=temp/60 + "." + temp%60;
                    }
            }
            addfile((executive+1)+"\t\t\tDE"+ (executive+1) + "\t\t\t\t"+res+"\t\t\t\t" +des+ "\t\t\t\t"+ order +"\t\t\t\t"+pickup+"\t\t\t\t"+ drop +"\t\t\t\t" + (price+50)+"\n");
            
        }

        public static void Printtotal(List<Customer> arr, int executive){
            int price=0;
            int allow=10;
            for(Customer i:arr){
                if(i.Executiveid==executive){
                        price+=i.cost;
                }
            }
            addfile("DE"+(executive+1)+"\t\t\t\t"+allow+"\t\t\t\t\t"+(price+50)+"\t\t\t\t"+(price+allow+50)+"\n");
        }

        public static void addfile(String s){
            try{
                FileWriter f = new FileWriter("./test.txt",true);
                f.write(s);
                f.close();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        public static int Noofexe(List<Customer> arr){
            int max=Integer.MIN_VALUE;
            for(Customer i:arr){
                max=Math.max(max, i.Executiveid);
            }
            return max;
        }
        public static void main(String[] args) {
            Scanner s=new Scanner(System.in);
           
            List <Customer>  all=new ArrayList<>();
            while(true){
                System.out.println("Enter choice 1. Add order 2.Display Executives ");
                int n=s.nextInt();
                switch(n){
                    case 1:{
                        System.out.println("Enter Restaurant Name");
                        char Res=s.next().charAt(0);
                        System.out.println("Enter Dropping Name");
                        char Drop=s.next().charAt(0);
                        System.out.println("Enter Time");
                        String time=s.next();
                        Customer cus=new Customer(Res, Drop, time);
                        addCustomer(cus, all);
                        break;
                    }
                    case 2:{
                        int num=Noofexe(all);
                        addfile("Delivery History\n");
                        String st = "Trip       Executive    Restaurant    Destination Point    Orders        Pick-up time    Delivery-time     Delivery Charge\n";                        
                        addfile(st);
                        for(int i=0;i<=num;i++){
                            Printall(all, i);
                        }
                        addfile("Total Earned\n");
                        addfile("Executive     Allowance     Deliver Charges      Total\n");
                        for(int i=0;i<=num;i++){
                            Printtotal(all, i);
                        }
                        System.out.println("Executed Successfully");
                        break;
                    }

                }
            }
        }
}