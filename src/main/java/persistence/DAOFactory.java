package persistence;


public class DAOFactory {

    static private DAOFactory instance = null;

    private DAOFactory() {}

    static public DAOFactory getInstance(){
        if(instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public UserDAO makeUserDAO()
    {
        return new UserDAO();
    }

    public GameDAO makeGameDAO()
    {
        return new GameDAO();
    }

    public ReviewDAO makeReviewDAO()
    {
        return new ReviewDAO();
    }

    public HoursPlayedDAO makeHoursPlayedDAO()
    {
        return new HoursPlayedDAO();
    }

    public PurchaseDAO makePurchaseDAO()
    {
        return new PurchaseDAO();
    }

    public ScoreDAO makeScoreDAO()
    {
        return new ScoreDAO();
    }

    public TagDAO makeTagDao() { return new TagDAO(); }

}
