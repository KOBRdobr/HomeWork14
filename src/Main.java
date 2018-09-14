import ru.zinnurov.VendingMachine;
import ru.zinnurov.drinks.DrinkType;
import java.util.Random;
import java.util.Scanner;
import org.apache.log4j.Logger;



public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    private static VendingMachine vm = new VendingMachine();

    public static void main(String[] args) {
        LOG.info("Запуск прогрммы");
        for (String line : vm.getDrinkTypes()) {
            System.out.println(line);
        }

        Scanner scan = new Scanner(System.in);
        printHelp();
        while (scan.hasNext()) {
            String command = scan.next();
            switch (command) {
                case "add": {
                    int money;
                    if(scan.hasNextInt()) {
                        money = scan.nextInt();
                        processAddMoney(money);
                    } else System.out.println("Введите add + {число}");
                    break;
                }
                case "get": {
                    int key;
                    if(scan.hasNextInt()) {
                        key = scan.nextInt();
                        processGetDrink(key);
                    } else System.out.println("Введите get + {число}");
                    break;
                }
                case "end": {
                    processEnd();
                    return;
                }
                default:
                    System.out.println("Команда не определена");
            }
            scan.nextLine();
        }
    }

    /**
     * обработка добавления денег в автомат
     * @param money - сумма
     */
    private static void processAddMoney(int money) {
        LOG.info("Пополнение для покупки");
        Random rand = new Random();
        if(rand.nextInt(10) > 5 ) {
            LOG.error("Зажевало деньги");
            throw new RuntimeException("Упс ваши деньги зажевало! Держите обратно ваши " + money + " руб.");
        }
        else System.out.println("Текущий баланс: " + vm.addMoney(money));
    }

    /**
     * обработка получения напитка
     * @param key - код напитка в автомате
     */
    private static void processGetDrink(int key) {
        DrinkType drinkType = vm.giveMeADrink(key);
        if (drinkType != null) {
            LOG.info("Выдача напитка");
            System.out.println("Ммм! " + drinkType.getName() + "!");
        }
    }

    /**
     * обработка получения сдачи
     */
    private static void processEnd() {
        System.out.println("Ваша сдача: " + vm.getChange());
    }

    /**
     * выводит подсказку по доступным командам
     */
    private static void printHelp() {
        System.out.println( "Введите 'add <количество>' для добавления купюр" );
        System.out.println( "Введите 'get <код напитка>' для получения напитка" );
        System.out.println( "Введите 'end' для получения сдачи" );
    }
}
