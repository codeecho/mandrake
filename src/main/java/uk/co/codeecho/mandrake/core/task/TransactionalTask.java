package uk.co.codeecho.mandrake.core.task;

import biz.devspot.entity.framework.core.EntityManagerFactory;

public abstract class TransactionalTask implements Task{

    @Override
    public void run() {
        EntityManagerFactory.getManager().openTransaction();
        doRun();
        EntityManagerFactory.getManager().commitTransaction();
    }
    
    public abstract void doRun();

}
