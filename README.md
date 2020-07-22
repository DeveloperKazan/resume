DESCRIPTION OF PROGECT «RESUME»

Установил зависимости в pom.xml: spring-context-support, spring-webmvc, hibernate-entitymanager и др. см. pom.xml
class ServiceConfig с указанием в @ComponentScan пакетов, которые Spring должен просканировать.           
@Bean static PropertySourcesPlaceholderConfigurer() указывает Spring текстовый ресурс с дополнительными настройками (файл application.properties). For example: class ApplicationListener для поля private boolean production, указано свойство для дополнительной настройки @Value("${application.production}"). 

В пакете service создал классы с аннотацией @Service, для которых Spring будет создавать beans со Scope Singleton по умолчанию. 
В пакете controller классы пометил аннотацией @Controller

@Controller
public class EditProfileController – установил зависимость @Autowired
private EditProfileService editProfileService

@RequestMapping над методами getEditProfile(), getEditSkills(Model model), saveEditSkills(), getMyProfile(), с указанием URL, а также методов запроса GET/POST

Создал классы (@Component) в пакетах filter, listener. 
class ResumeWebApplicationInitializer implements WebApplicationInitializer – класс для инициализации Spring контейнера в методе onStartup(ServletContext container)
и создания WebApplicationContext ctx
scan("resume.net.configuration") – Spring просканирует все файлы пакета

//WebApplicationContext – это Spring IoC container, в котором есть beans controllers, //ViewResolver, HandlerMapping (на основании URL определяет соответствующий //контроллер). Он может также ссылаться на другой Spring IoC container с бинами //services, datasources etc. - применимо в архитектуре более сложного приложения

Когда Spring полнимет context, в contextInitialized(ServletContextEvent) значение переменной @Value("${application.production}") запишется в атрибут context.
К переменной можно обратиться в page-template.jsp через tag<h1>${production }</h1>  
 
class ResumeWebApplicationInitializer implements WebApplicationInitializer
в методе которого registerSpringMVCDispatcherServlet регистрируется DispatherServlet с mapping “/” на ROOT. Можно использовать web.xml, но я использовал Java конфигурацию
@Configuration - информируем Spring что данный bean (class MVCConfig extends WebMvcConfigurerAdapter) является java-конфигурацией

@Configuration 
class MVCConfig extends WebMvcConfigurerAdapter – конфигурирование MVC модуля
@EnableWebMvc – поддержка SpringWebMVC
@ComponentScan({ "resume.net.controller" }) – информирую контейнер где будут распологаться все контроллеры

@Bean
public ViewResolver – в данном бине определил путь к папке где будут храниться все файлы с расширением .jsp (уровень представления), для передачи в контроллер.

@Bean
public MessageSource messageSource() – кодировка всех сообщений "UTF-8"

@Bean
public LocalValidatorFactoryBean localValidatorFactoryBean() – валидация данных

@Bean
CommonsMultipartResolver multipartResolver() – для загрузки файлов на сервер

В методе addResourceHandlers(ResourceHandlerRegistry registry) задаю mapping по URL для соответствующих ресурсов: "/static/**" "/media/**" "/favicon.ico".

Правило формирования URL – файл URL-RULES.txt. Разделение на три группы URL: 

class ServiceConfig – конфигурация сервиса. @ComponentScan - указал пакеты для сканирования 

@Controller
class EditProfileController – контроллер для добавления профиля

//EXTRA: @RequestMapping(value="/{uid}", headers="Content-Type application/JSON")
//с помощью рефлексии в сигнатуру метода можно передать Request, response, session //objects (Servlet API), multipart file и др.

@Autowired
private EditProfileService editProfileService() – установил зависимость

Class PublicDataController – обрабатывает Get-запрос. 
С помощью рефлексии @RequestMapping(value="/{uid}", method=RequestMethod.GET), по UID находит зарегистрированного пользователя

class NameServiceImpl – преобразование имени из полученной модели БД. Тримирую ‘-’, и привожу первые буквы к верхнему регистру (логика пока нужна для наглядности)

(ErrorHandler) class ResumeFilter extends AbstractFilter – помечен как @Component. ErrorHandler - обработчик ошибок устанавливается на самом верхнем уровне. Принимает первым все запросы от пользователя. abstract class AbstractFilter implements Filter - методы init(), destroy(), doFilter(), где используется не HTTPServletRequest, а базовые ServletRequest поэтому их необходимо кастовать к HTTPServletRequest
private void handleException() – если окружение application.production, управление передаётся по URL "/error?url=" 


ORM/JPA/Spring Data

Установил зависимости в pom.xml: hibernate-entitymanager, spring-data-jpa, 
commons-dbcp2 - implements Database Connection Pooling (компонент позволяет повторно использовать Pool of connections)
@Configuration
@PropertySource("classpath:application.properties") – все переменные будут считаны
@EnableTransactionManagement – поддержка менеджера транзакций
@EnableJpaRepositories("resume.net.repository.storage") – для поддержки JPA автоматического выполнения методов интерфейсов в папке storage (keyword methods)
public class JPAConfig – конфигурирование JPA

@Autowired
private Environment environment - настройка окружения application.properties
(настройки подключения к БД, dialect hibernate для PostgreSQL, начальное и максимальное количество соединений в pool)

@Bean(/*destroyMethod="close"*/)
public DataSource dataSource() – объект для хранения подключений к БД и автоматического их закрытия. destroyMethod="close – можно не указывать, так как объект @Bean DataSource автоматически вызовет close() или shutdown() и закроет все подключения к БД

private Properties hibernateProperties() – указывает hibernate.dialect
properties.put("javax.persistence.validation.mode", "none") – дополнительная настройка, для того чтобы Hibernate не выполнял валидацию данных перед сохранением (по умолчанию), так как за валидацию будет отвечать компонент SpringValidator перед сохранением сущности в БД

@Bean
public LocalContainerEntityManagerFactoryBean entityManagerFactory() – метод создаёт entityManagerFactory

// по мере описания процесса разработки данные буду добавлять в файл README.md
