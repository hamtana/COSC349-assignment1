package web;

import dao.TenantDAO;
import helpers.ScryptHelper;
import io.jooby.Jooby;
import domain.Tenant;
import io.jooby.StatusCode;

import java.nio.CharBuffer;

public class TenantModule extends Jooby {

    public TenantModule(TenantDAO dao){


        post("api/tenants", ctx -> {
            Tenant tenant = ctx.body().to(Tenant.class);
            String password = tenant.getPassword();
            CharBuffer hash = ScryptHelper.hash(password);
            tenant.setPassword(hash.toString());
            dao.saveTenant(tenant);
            return ctx.send(StatusCode.CREATED);

        });


    }
}