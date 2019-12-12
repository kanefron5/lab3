import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.RequestingUserName;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@ManagedBean(name = "Bean", eager = true)
@ApplicationScoped
public class ManageBean implements Serializable {


    private static final long serialVersionUID = 1L;
    private boolean x1; //-4
    private boolean x2; //-3
    private boolean x3; //-2
    private boolean x4; //-1
    private boolean x5; //0
    private boolean x6; //1
    private boolean x7; //2
    private boolean fromCanvas = false;
    private Double y = null;
    private ArrayList<Dot> dots = new ArrayList<>();
    private String R = String.valueOf(1);
    private String userX;
    private String userY;


    public String getUserX() {
        return userX;
    }

    public void setUserX(String userX) {
        this.userX = userX;
    }

    public String getUserY() {
        return userY;
    }

    public void setUserY(String userY) {
        this.userY = userY;
    }

    public boolean isFromCanvas() {
        return fromCanvas;
    }

    public void setFromCanvas(boolean fromCanvas) {
        this.fromCanvas = fromCanvas;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getJsonDots() {

        StringBuilder s = new StringBuilder("[ ");
        for (Dot dot : dots) {
            s.append(String.format("{\"x\":%s, \"y\":%s, \"r\":%s, \"popadanie\":%s},", dot.getX(), dot.getY(), dot.getR(), dot.isPopadanie()));
        }
        return s.substring(0, s.length() - 1) + "]";
    }

    public ArrayList<Dot> getDots() {
        dots = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://127.0.0.1:54321/postgres?loggerLevel=OFF";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statmt = connection.prepareStatement("CREATE TABLE if not exists dots (id serial primary key , x real, y real, r real, popadanie INTEGER, user_id text);");
            statmt.execute();
            String user = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dots WHERE user_id=?;");
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double r = resultSet.getDouble("r");
                boolean popadanie = resultSet.getInt("popadanie") == 1;
                dots.add(new Dot(x, y, r, popadanie));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return dots;
    }

    public void setDots(ArrayList<Dot> dots) {
        this.dots = dots;
    }


    public String getR() {
        return R.replace(",", ".");
    }

    public void setR(String r) {
        R = r.replace(",", ".");
    }

    public String getY() {
        if(isFromCanvas()){
            return userY;
        }
        return (y == null) ? "" : y.toString();
    }

    public boolean isX1() {
        fromCanvas = false;
        return x1;
    }

    public void setX1(boolean x1) {
        this.x1 = x1;
    }

    public boolean isX2() {
        return x2;
    }

    public void setX2(boolean x2) {
        this.x2 = x2;
    }

    public boolean isX3() {
        return x3;
    }

    public void setX3(boolean x3) {
        this.x3 = x3;
    }

    public boolean isX4() {
        return x4;
    }

    public void setX4(boolean x4) {
        this.x4 = x4;
    }

    public boolean isX5() {
        return x5;
    }

    public void setX5(boolean x5) {
        this.x5 = x5;
    }

    public boolean isX6() {
        return x6;
    }

    public void setX6(boolean x6) {
        this.x6 = x6;
    }

    public boolean isX7() {
        return x7;
    }

    public void setX7(boolean x7) {
        this.x7 = x7;
    }

    private double getX() {
        if(isFromCanvas()){
            return Double.parseDouble(userX.replace(",", "."));
        }
        boolean[] b = new boolean[]{x1, x2, x3, x4, x5, x6, x7};
        int[] is = new int[]{-4, -3, -2, -1, 0, 1, 2};
        for (int i = 0; i < b.length; i++) {
            boolean b1 = b[i];
            if (b1) return is[i];
        }
        return 0;
    }

    public void setY(String y) {
        if (y.isEmpty())
            return;
        double yd = Double.parseDouble(y.trim().replace(",", "."));
        System.out.println(yd);
        if (fromCanvas)
            this.y = yd;
        else {
            if (yd > -3 && yd < 5)
                this.y = yd;
            else
                this.y = yd;
        }
    }


    public String addToList() {
        Dot dot = new Dot(getX(), getY(), R);
        System.out.println(dot);
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://127.0.0.1:54321/postgres?loggerLevel=OFF";
            Connection connection = DriverManager.getConnection(url);

            PreparedStatement statmt = connection.prepareStatement("CREATE TABLE if not exists dots (id serial primary key , x real, y real, r real, popadanie INTEGER, user_id text);");
            statmt.execute();

            String user = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);

            PreparedStatement st = connection.prepareStatement("INSERT INTO dots (x, y, r, popadanie, user_id) values(?, ?, ?, ?, ?)");
            st.setDouble(1, dot.getX());
            st.setDouble(2, dot.getY());
            st.setDouble(3, dot.getR());
            st.setInt(4, dot.isPopadanie() ? 1 : 0);
            st.setString(5, user);

            st.executeUpdate();
            st.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "check.xhtml?faces-redirect=true";
    }

}