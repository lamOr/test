package GOLF;
import UserMath.UserMath;

public class DistanceCaculation {
    private static double constance=111198.923;
    ///caculate the distance between two coordinater. unit is m
    public static double D_jw(double wd1,double jd1,double wd2,double jd2)
    {
        double x,y,out;
        double PI=3.14159265;
        double R=6.371229*1e6;
        x=(jd2-jd1)*PI*R*Math.cos( ((wd1+wd2)/2) *PI/180)/180;
        y=(wd2-wd1)*PI*R/180;
        out=UserMath.hypot(x,y);
        return out;
    }
   public static Point computation(double STARTLAT,double STARTLONG, double ANGLE1, double DISTANCE){
        double a,b,c,alpha,e,e2,W,V,B1,L1,B2=0,L2=0,A1,A2,W1,E1,S;
        double sinu1,cosu1,sinA0,cotq1,sin2q1,cos2q1,cos2A0,sinu2;
        double k2, aa, BB, CC, EE22, AAlpha, BBeta,theta,lamuda;
        double q0,sin2q1q0,cos2q1q0,q;

        B1 = STARTLAT;
        L1 = STARTLONG;
        A1 = ANGLE1;
        S = DISTANCE;

        a = 6378245;
        b = 6356752.3142;

        c = a*a / b;
        alpha = (a - b) / a;
        e = Math.sqrt(a*a - b*b)/a;
        e2 = Math.sqrt(a*a - b*b) / b;

        B1 = B1*Math.PI /180;
        L1 = L1*Math.PI /180;
        A1 = A1*Math.PI /180;

        W = Math.sqrt(1 - UserMath.pow(Math.sin(B1)*e,2));
         // W = Sqr(1 - e ^ 2 * (Sin(B1) ^ 2))
        V = W * (a / b);

        E1 = e;
        W1 = W;

        sinu1 = Math.sin(B1) * Math.sqrt(1 - E1 * E1) / W1;
        cosu1 = Math.cos(B1) / W1;
        sinA0 = cosu1 * Math.sin(A1);
        cotq1 = cosu1 * Math.cos(A1);
        sin2q1 = 2 * cotq1 / (cotq1*cotq1 + 1);
        cos2q1 = (cotq1*cotq1 - 1) / (cotq1*cotq1 + 1);
        cos2A0 = 1 - sinA0*sinA0 ;
        e2 = Math.sqrt(a*a - b*b) / b;
        k2 = e2 * e2 * cos2A0;

        aa = b * (1 + k2 / 4 - 3 * k2 * k2 / 64 + 5 * k2 * k2 * k2 / 256);
        BB = b * (k2 / 8 - k2 * k2 / 32 + 15 * k2 * k2 * k2 / 1024);
        CC = b * (k2 * k2 / 128 - 3 * k2 * k2 * k2 / 512);
        e2 = E1 * E1;
        AAlpha = (e2 / 2 + e2 * e2 / 8 + e2 * e2 * e2 / 16) - (e2 * e2 / 16 + e2 * e2 * e2 / 16) * cos2A0 + (3 * e2 * e2 * e2 / 128) * cos2A0 * cos2A0;
        BBeta = (e2 * e2 / 32 + e2 * e2 * e2 / 32) * cos2A0 - (e2 * e2 * e2 / 64) * cos2A0 * cos2A0;

        q0 = (S - (BB + CC * cos2q1) * sin2q1) / aa;
        sin2q1q0 = sin2q1 * Math.cos(2 * q0) + cos2q1 * Math.sin(2 * q0);
        cos2q1q0 = cos2q1 * Math.cos(2 * q0) - sin2q1 * Math.sin(2 * q0);
        q = q0 + (BB + 5 * CC * cos2q1q0) * sin2q1q0 / aa;
        theta = (AAlpha * q + BBeta * (sin2q1q0 - sin2q1)) * sinA0;

        sinu2 = sinu1 * Math.cos(q) + cosu1 * Math.cos(A1) * Math.sin(q);
        B2 = UserMath.atan(sinu2 / (Math.sqrt(1 - E1 * E1) * Math.sqrt(1 - sinu2 * sinu2))) * 180 / Math.PI;
        lamuda = UserMath.atan(Math.sin(A1) * Math.sin(q) / (cosu1 * Math.cos(q) - sinu1 * Math.sin(q) * Math.cos(A1))) * 180 / Math.PI;


        if(Math.sin(A1)>0){
            if(Math.sin(A1)* Math.sin(q) / (cosu1 * Math.cos(q) - sinu1 * Math.sin(q) * Math.cos(A1)) > 0)
                lamuda = Math.abs(lamuda);
            else
                lamuda = 180 - Math.abs(lamuda);
        }
        else{
            if(Math.sin(A1) * Math.sin(q) / (cosu1 * Math.cos(q) - sinu1 * Math.sin(q) * Math.cos(A1)) > 0)
                lamuda = Math.abs(lamuda) -180;
            else
                lamuda = -(Math.abs(lamuda));
        }

        L2 = L1 * 180 /Math.PI + lamuda - theta * 180 / Math.PI;
        Point endPoint = new Point();
        endPoint.setLat(B2);
        endPoint.setLog(L2);
        return endPoint;
    }
    public static void distanceToCoordinate(double lat1, double log1,double distance,double x){
        double lat2,log2;
        lat2=distance * Math.cos(x*Math.PI/180)/constance+lat1;
        log2=distance * Math.sin(x*Math.PI/180)/constance+log1;
        System.out.println("lat:"+lat2+"log:"+log2);
    }
}
