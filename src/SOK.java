import java.util.Scanner;
import java.util.Vector;


public class SOK {


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Vector base = new Vector(); //основания
        Vector basis = new Vector();
        Vector SOK = new Vector();

        int value;
        int P_rab = 1;
        int P_poln = 0;
        int amount_base;
        int B;
        int A = 0;
        boolean check = false;

        System.out.println("Введите количество информационных оснований: ");
        int n = scan.nextInt();
        while (!check) {
            base.clear();
            P_rab = 1;
            System.out.println("Поочередно введите информационные основания: ");
            for (int i = 0; i < n; i++) {
                value = scan.nextInt();
                base.add(value);
                P_rab *= value;
            }
            System.out.println("Введите контрольное основание: ");
            value = scan.nextInt();
            base.add(value);
            P_poln = P_rab * value;

            check = inputError(base);
        }

        amount_base = n + 1;
        for (int i = 0; i < amount_base; i++) { //вычисление базисов
            B = basis(P_poln, (int)base.get(i));
            basis.add(B);
        }

        System.out.println("Базиы: " + basis);

        System.out.printf("Введи число меньше, чем %d: ", P_rab);
        value = scan.nextInt();
        for (int i = 0; i < amount_base; i++) {
            SOK.add(value % (int) base.get(i));
        }
        System.out.printf("Число %d в модулярном коде: %s", value, SOK);


        System.out.printf("\nВведите номер остатка для введения ошибки (от 1 до %d): ", amount_base);
        int err_num = scan.nextInt();
        System.out.printf("Введите глубину ошибки (от 1 до %s): ", (int)base.get(err_num - 1) - 1);
        int depth = scan.nextInt();
        int balance_error = ((int)SOK.get(err_num - 1) + depth) % (int)base.get(err_num - 1);
        SOK.set(err_num - 1, balance_error);
        System.out.printf("Число %d в модулярном коде с ошибкой: %s", value, SOK);


        for (int i = 0; i < amount_base; i++) { //переводим число с ошибкой, чтобы найти интервал
            A += ((int)SOK.get(i) * (int)basis.get(i));
        }
        A = A % P_poln;
        System.out.printf("\nПри переводе модулярного кода в позиционный было получено значение %s, что превышает " +
                "значение рабочего диапазона, равного %s.\nМатематически доказано, что в коде СОК присутствует " +
                "ошибка", A, P_rab );
    }

    public static int basis(int P_poln, Object base) {
        int p = (int) base;
        int P = P_poln / p; //константа
        int b = P % p; //остаток
        int m = 1; //вес
        for (int k = 0; k != 1; m++) {
            k = (m * b) % p;
            if (k == 1) {
                break;
            }
        }
        int B = m * P; //базис
        return B;
    }

    public static boolean inputError(Vector base) { //проверка делителей
        Vector baseDivisors = new Vector();
        int b;
        boolean result = true;

        for (Object bas : base) { //берем каждое основание
            b = (int)bas; //приводим его к инту
            for (int i = 2; i <= b; i++) { //делим основание на каждое число от двух до основания
                if (b % i == 0) { //(единица не в счет: она всегда их НОД)
                    baseDivisors.add(i); //если число делит без остатка основание - добавляем его в вектор
                }
            }
        }

        for (int i = 0; i < baseDivisors.size(); i++) { //ищем одинаковые значения
            for (int k = i + 1; k < baseDivisors.size(); k++) {
                if ((int)baseDivisors.get(i) == (int)baseDivisors.get(k)) {

                    System.out.println("Ошибка ввода. Все снования должны быть взаимно простыми");
                    result = false;
                    break;
                }
                if (!result) break;
            }
        }
        return result;
    }

}
