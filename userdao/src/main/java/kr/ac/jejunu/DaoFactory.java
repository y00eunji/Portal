package kr.ac.jejunu;

public class DaoFactory {
    public UserDao getUserDao(){
        return new UserDao(new JejuConnectionMaker());
    }

    public JejuConnectionMaker getConnectionMaker(){
        return new JejuConnectionMaker();
    }
}
