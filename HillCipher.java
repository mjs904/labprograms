import java.util.*;

class HillCipher {
    static int M(int x){return (x%26+26)%26;}
    static int D(int[][] a){
        return a[0][0]*(a[1][1]*a[2][2]-a[1][2]*a[2][1])
             - a[0][1]*(a[1][0]*a[2][2]-a[1][2]*a[2][0])
             + a[0][2]*(a[1][0]*a[2][1]-a[1][1]*a[2][0]);
    }
    static int I(int d){
        for(int i=1;i<26;i++) if(M(d*i)==1) return i;
        throw new RuntimeException("Not invertible");
    }
    static int[][] inv(int[][] k){
        int d=I(D(k));
        int[][] i={
            {k[1][1]*k[2][2]-k[1][2]*k[2][1], k[0][2]*k[2][1]-k[0][1]*k[2][2], k[0][1]*k[1][2]-k[0][2]*k[1][1]},
            {k[1][2]*k[2][0]-k[1][0]*k[2][2], k[0][0]*k[2][2]-k[0][2]*k[2][0], k[0][2]*k[1][0]-k[0][0]*k[1][2]},
            {k[1][0]*k[2][1]-k[1][1]*k[2][0], k[0][1]*k[2][0]-k[0][0]*k[2][1], k[0][0]*k[1][1]-k[0][1]*k[1][0]}
        };
        for(int r=0;r<3;r++) for(int c=0;c<3;c++) i[r][c]=M(i[r][c]*d);
        return i;
    }
    static String run(String t,int[][] k){
        t=t.toUpperCase().replaceAll("[^A-Z]","");
        while(t.length()%3!=0)t+="X";
        StringBuilder s=new StringBuilder();
        for(int i=0;i<t.length();i+=3)
            for(int r=0;r<3;r++){
                int sum=0;
                for(int c=0;c<3;c++) sum+=k[r][c]*(t.charAt(i+c)-'A');
                s.append((char)(M(sum)+'A'));
            }
        return s.toString();
    }
    public static void main(String[] a){
        Scanner sc=new Scanner(System.in);
        int[][] k=new int[3][3];
        for(int i=0;i<3;i++)for(int j=0;j<3;j++)k[i][j]=sc.nextInt();
        sc.nextLine();
        String p=sc.nextLine();
        String c=run(p,k);
        System.out.println(c);
        System.out.println(run(c,inv(k)));
    }
}