package utf8.citicup.dataService;

import utf8.citicup.domain.entity.Portfolio;

import java.util.List;

public interface PortfolioDataService {
    //基本操作
    public Portfolio save(Portfolio portfolio);
    public void delete(long id);
    public Portfolio findById(long id);
    public List<Portfolio> findAll();
    //额外操作
    public List<Portfolio> findByUsername(String username);

    public Portfolio updateTrackingStatus(long id, boolean trackingStatus);

    public Portfolio updateName(long id, String portfolioName);
}
