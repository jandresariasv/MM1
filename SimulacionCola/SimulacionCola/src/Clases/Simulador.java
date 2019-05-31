package Clases;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Simulador {
        
    ArrayList<Grafica> arrayList = new ArrayList<Grafica>();//creamos el objeto lista
    
    private static double lambda;
    private static double miu;
    private static int clientes;
    private static double tiempo;
    
    public static int a  ;
    public static int b  ;
    public static int m ;
    public static double x ;

    private static double ti; //random arrival time
    private static double si; //random service time
    private static double tl; //cumulative arrival time
    private static double te; //time at which the object enters the server
    private static double ts; //time at which the object exits the server
    private static double tts; //total time in the system
    private static double twq; //time waiting in the queue
    private static double sit; //server idle time
    private static double ns; //number of subjects in the queue
    private static double ms; //number of subjects in the system 
    private static double tll;
    
    
    public static double si_cont = 0.0; //tiempo total del sistema
    public static double ti_cont = 0.0; //tiempo de espera  cola
    public static double twq_cont = 0; //elementos sistema
    public static double tts_cont = 0; //elementos cola

    public static double simulado_rho = 0;
    public static double simulado_ws = 0;
    public static double simulado_wq = 0;
    public static double simulado_ls = 0;
    public static double simulado_lq = 0;
       
    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getMiu() {
        return miu;
    }

    public void setMiu(double miu) {
        this.miu = miu;
    }

    public int getClientes() {
        return clientes;
    }

    public void setClientes(int clientes) {
        this.clientes = clientes;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getSimulado_rho() {
        return simulado_rho;
    }

    public void setSimulado_rho(double simulado_rho) {
        this.simulado_rho = simulado_rho;
    }

    public double getSimulado_ws() {
        return simulado_ws;
    }

    public void setSimulado_ws(double simulado_ws) {
        this.simulado_ws = simulado_ws;
    }

    public double getSimulado_wq() {
        return simulado_wq;
    }

    public void setSimulado_wq(double simulado_wq) {
        this.simulado_wq = simulado_wq;
    }

    public double getSimulado_ls() {
        return simulado_ls;
    }

    public void setSimulado_ls(double simulado_ls) {
        this.simulado_ls = simulado_ls;
    }

    public double getSimulado_lq() {
        return simulado_lq;
    }

    public void setSimulado_lq(double simulado_lq) {
        this.simulado_lq = simulado_lq;
    }
    
    public double p() {
        double p = this.getLambda() / this.getMiu();
        return p;
    }

    public double ls() {
        double ls = this.getLambda() / (this.getMiu() - this.getLambda());
        return ls;
    }

    public double lq() {
        double lq = Math.pow(this.getLambda(), 2) / (this.getMiu() * (this.getMiu() - this.getLambda()));
        return lq;
    }

    public double ws() {
        double ws = 1 / (this.getMiu() - this.getLambda());
        return ws;
    }

    public double wq() {
        double wq = this.getLambda() / (this.getMiu() * (this.getMiu() - this.getLambda()));
        return wq;
    }
    
    
    public Double Formato(Double Int) {
        DecimalFormat formato1 = new DecimalFormat("#.00000");
        return Double.parseDouble(formato1.format(Int).replace(",","."));
    }
    
    public Double aleatorio() {
        double ale = ((this.getX() * this.getA()) + this.getB()) % this.getM();         
        this.setX(ale);
        double n = this.getX() / this.getM();  
        return n;
    }    
    
    
    public ArrayList<Grafica> getArrayList() {
        return arrayList;
    }
    
    public void simulation() {
        
        Double Simulado_rho = 0.0;
        Double Simulado_lq = 0.0;
        Double Simulado_wq = 0.0;
        Double Simulado_ls = 0.0;
        Double Simulado_ws = 0.0;
        
        for (int i = 1; i <= this.getTiempo(); i++) {
            
            te = 0; 
            ts = 0; 
            tts = 0; 
            twq = 0; 
            sit = 0; 
            ns = 0; 
            ms = 0; 
            tll = 0;
            tts_cont = 0;
            twq_cont = 0;
            si_cont = 0;
            ti_cont = 0;

            for (int j = 1; j <= this.getClientes(); j++) {
                ti = -Math.log(1 - this.aleatorio()) / this.getLambda(); 
                si = -Math.log(1 - this.aleatorio()) / this.getMiu();
                tll += ti;
                if (j == 1) {
                    te = tll;
                    ts = si + tll;
                    twq = 0;
                    tts = si;
                    sit = 0;
                    ns = 1;
                    ms = 0;
                } else {
                    if (tll >= ts) {
                        te = tll;
                        sit = te - ts;
                        ts = si + tll;
                        twq = 0;
                        tts = si;
                        ns = 1;
                        ms = 0;
                    } else {
                        twq = ts - tll;
                        te = tll + twq;
                        ts = ts + si;
                        tts = twq + si;
                        sit = 0;
                        ms = ns;
                        ns = ms + 1;
                        //diego
                        Grafica e = new Grafica();
                        e.setTiempo(i);
                        e.setCliente(j);
                        e.setEn_cola(this.ns);
                        e.setEn_sistema(this.ms);
                        this.arrayList.add(e);
                        
                    }
                }
                tts_cont += tts; 
                twq_cont += twq; 
                ti_cont += ti;
                si_cont += si;                   
            }
                        
            Simulado_rho += Formato((this.si_cont / this.getClientes()) / (this.ti_cont / this.getClientes()));
            Simulado_lq += Formato(this.getSimulado_lq() + (this.twq_cont / this.te));
            Simulado_wq += Formato(this.getSimulado_wq() + (this.twq_cont / this.getClientes()));
            Simulado_ls += Formato(this.getSimulado_ls() + (this.tts_cont / this.ts));
            Simulado_ws += Formato(this.getSimulado_ws() + (this.tts_cont / this.getClientes()));
        }
            this.setSimulado_rho(Simulado_rho/this.getTiempo());
            this.setSimulado_lq(Simulado_lq/this.getTiempo());
            this.setSimulado_wq(Simulado_wq/this.getTiempo());
            this.setSimulado_ls(Simulado_ls/this.getTiempo());
            this.setSimulado_ws(Simulado_ws/this.getTiempo());
    }
    
    

    
}
