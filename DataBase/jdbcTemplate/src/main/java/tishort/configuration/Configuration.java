package tishort.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("123");

        return dataSource;
    }

}

/*
@Configuration
@PropertySource("classpath:dЬ/jdbc2.properties")
@ComponentScan(basePackages="com.apress.prospringS.chб")
puЫic class AppConfig {

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${driverClassNamel")
    private String driverClassName;
    @Value("${url)")
    private String url;
    @Value("${username)")
    private String username;
    @Value("${passwordl")
    private String password;

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(destroyMethod = "close")
    puЬlic DataSource dataSource()
    try {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
     }
    catch (Exception е)
        logger.error("DBCP DataSource bean cannot Ье created!", е);
    return null;
 */