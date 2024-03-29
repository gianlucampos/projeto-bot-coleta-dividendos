package br.com.main;

import com.google.gson.Gson;
import dev.botcity.framework.bot.DesktopBot;
import dev.botcity.maestro_sdk.BotExecutor;
import dev.botcity.maestro_sdk.runner.BotExecution;
import dev.botcity.maestro_sdk.runner.RunnableAgent;

import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BotColetaDividendos extends DesktopBot implements RunnableAgent {

    private final ArrayList<Dividend> dividendList = new ArrayList<>();
    private Dividend currentDividend = Dividend.builder().build();
    private static final Dividend target = Dividend.builder().dtActive("10/11/2022").codeActive("AGNC").build();
    private static final String PATH = "./src/resources/Reports.json";

    public BotColetaDividendos() {
        enableDebug();
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
            if (!find("dtAtivo", 0.95, 3000)) {
                notFound();
                throw new Exception();
            }
            tripleClickRelative(5, 50);

            while (!currentDividend.equals(target)) {
                selectDividendLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            createJsonReport();
        }
    }

    private void selectDividendLine() throws Exception {
        controlC();

        Dividend dividend = new Dividend();
        String[] valoresIniciais = getClipboard().split(" ");
        String[] valoresFinais = getClipboard().split("R\\$ ");
        dividend.setDtActive(valoresIniciais[0]);
        dividend.setCodeActive(valoresIniciais[1]);
        dividend.setValue(valoresFinais[2]);
        dividend.setTaxes(valoresFinais[3]);

        if (dividend.equals(currentDividend)) {
            if (findText("lastPageAnchor", 230, 1000)) {
                keyEnd();
            }
            typeKeys(KeyEvent.VK_PAGE_DOWN);
            if (!find("dtAtivo", 0.95, 3000)) {
                notFound();
                throw new Exception();
            }
            tripleClickRelative(5, 50);
            controlC();
        } else {
            currentDividend = dividend;
            dividendList.add(dividend);
            clickAt(this.getLastX(), this.getLastY() + 40, 3);
        }
    }

    private void createJsonReport() {
        try (PrintWriter out = new PrintWriter(new FileWriter(PATH))) {
            String json = new Gson().toJson(dividendList);
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notFound() {
        System.out.println("Element not found: " + "dtAtivo");
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

