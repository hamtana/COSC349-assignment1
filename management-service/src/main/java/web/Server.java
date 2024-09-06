package web;

import dao.DAOFactory;
import dao.ManagerDAO;
import dao.PropertyDAO;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.StatusCode;
import io.jooby.handler.Cors;
import io.jooby.gson.GsonModule;
import io.jooby.handler.CorsHandler;

public class Server extends Jooby {

    private static final ManagerDAO managerDAO = DAOFactory.getManagerDAO();
    private static final PropertyDAO propertyDAO = DAOFactory.getPropertyDAO();

    public Server(){
        install(new GsonModule());

        Cors cors = new Cors()
                .setOrigin("*")
                .setMethods("GET", "POST", "PUT", "DELETE")
                .setHeaders("*");
        use(new CorsHandler(cors));

        mount(new ManagerModule(managerDAO));
        mount(new PropertyModule(propertyDAO));


        error(StatusCode.SERVER_ERROR, (ctx, cause, code) -> {
            ctx.getRouter().getLog().error(cause.getMessage(), cause);
            ctx.send("Server error: " + cause.getMessage());
        });
        
        mount(new StaticAssetModule());


    }

    public static void main(String [] args){
        System.out.println("\nStarting Server...");
        new Server()
                .setServerOptions(new ServerOptions().setPort(8081))
                .start();
    }


}

