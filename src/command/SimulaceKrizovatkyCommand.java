package command;

import casovani.Casovac;
import kolekce.KolekceException;
import krizovatka.IKrizovatka;
import krizovatka.KrizovatkaSimulator;

/**
 *
 * @author 
 */
public class SimulaceKrizovatkyCommand {

    private static int i = 0;

    public static void main(String[] args)
            throws KolekceException, InterruptedException {
        IKrizovatka krizovatka = new KrizovatkaSimulator();
        Casovac.instance().start();

        krizovatka.setHlaseniPrijezduZeSmeru(
                IKrizovatka.Smer.SEVER,
                s -> System.out.printf("T= %04d: Prijezd ze severu %s\n",i, s));
        krizovatka.setHlaseniOdjezduZeSmeru(
                IKrizovatka.Smer.SEVER,
                s -> System.out.printf("T= %04d: Odjezd ze severu %s\n", i, s));
        while (true) {
            Thread.sleep(1000);
            i++;

        }
    }
}
