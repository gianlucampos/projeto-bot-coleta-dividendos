package br.com.main;

import dev.botcity.framework.bot.DesktopBot;
import dev.botcity.maestro_sdk.BotExecutor;
import dev.botcity.maestro_sdk.runner.BotExecution;
import dev.botcity.maestro_sdk.runner.RunnableAgent;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BotColetaDividendos extends DesktopBot implements RunnableAgent {

    private final ArrayList<Dividend> dividendList = new ArrayList<>();
    private Dividend currentDividend = Dividend.builder().build();
    private Dividend lastDividend;

    public BotColetaDividendos() {
        try {
            setResourceClassLoader(this.getClass().getClassLoader());
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BotExecutor.run(new BotColetaDividendos(), args);
    }

    @Override
    public void action(BotExecution botExecution) {

        try {
            if (!findText("dtAtivo", 230, 3000)) {
                notFound("dtAtivo");
                throw new RuntimeException();
            }
            tripleClickRelative(5, 50);

            do {
                lastDividend = currentDividend;
                selectDividendLine();
                System.out.println(currentDividend);
            } while (!currentDividend.equals(lastDividend));

            System.out.println(dividendList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectDividendLine() {
        controlC();

        Dividend dividend = new Dividend();
        String[] valoresIniciais = getClipboard().split(" ");
        String[] valoresFinais = getClipboard().split("R\\$ ");
        dividend.setDtActive(valoresIniciais[0]);
        dividend.setNameActive(valoresIniciais[1]);
        dividend.setValue(valoresFinais[2]);
        dividend.setTaxes(valoresFinais[3]);

        if (dividend.equals(currentDividend)) {
            if (findText("lastPageAnchor", 230, 1000)) {
                keyEnd();
            }
            typeKeys(KeyEvent.VK_PAGE_DOWN);
            if (!findText("dtAtivo", 230, 1000)) {
                notFound("dtAtivo");
                throw new RuntimeException();
            }
            tripleClickRelative(5, 50);
            controlC();
        } else {
            currentDividend = dividend;
            dividendList.add(dividend);
            clickAt(this.getLastX(), this.getLastY() + 40, 3);
        }
    }


    private void notFound(String label) {
        System.out.println("Element not found: " + label);
    }

    private void load() {
        try {
            addImage("dtAtivo", "./src/resources/dtAtivo.png");
            addImage("lastPageAnchor", "./src/resources/lastPageAnchor.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

