/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sridhar
 */
// Target
interface PaymentProcessor {
    void processPayment(int amount);
}

// Adaptee
class PayPal {
    void payWithPayPal(int amount) {
        System.out.println("Paying " + amount + " using PayPal.");
    }
}

// Adapter
class PayPalAdapter implements PaymentProcessor {
    private PayPal payPal;

    public PayPalAdapter(PayPal payPal) {
        this.payPal = payPal;
    }

    @Override
    public void processPayment(int amount) {
        payPal.payWithPayPal(amount);
    }
}

public class AdapterPattern {
    public static void main(String[] args) {
        PayPal payPal = new PayPal();
        PaymentProcessor processor = new PayPalAdapter(payPal);
        processor.processPayment(100);
    }
}

