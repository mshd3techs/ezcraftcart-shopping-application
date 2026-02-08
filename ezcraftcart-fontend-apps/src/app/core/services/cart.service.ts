import { Injectable, signal, computed } from '@angular/core';
import { Product, CartItem } from '../../shared/models/product.model';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private readonly STORAGE_KEY = 'ezcraftcart-items';

    // Signals for reactive state
    private itemsSignal = signal<CartItem[]>(this.loadFromStorage());
    private isOpenSignal = signal<boolean>(false);

    // Public computed signals
    readonly items = this.itemsSignal.asReadonly();
    readonly isOpen = this.isOpenSignal.asReadonly();

    readonly totalItems = computed(() =>
        this.itemsSignal().reduce((total, item) => total + item.quantity, 0)
    );

    readonly totalPrice = computed(() =>
        this.itemsSignal().reduce((total, item) => total + (item.product.price * item.quantity), 0)
    );

    private loadFromStorage(): CartItem[] {
        try {
            const stored = localStorage.getItem(this.STORAGE_KEY);
            return stored ? JSON.parse(stored) : [];
        } catch {
            return [];
        }
    }

    private saveToStorage(): void {
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.itemsSignal()));
    }

    addItem(product: Product, quantity: number = 1): void {
        const currentItems = this.itemsSignal();
        const existingIndex = currentItems.findIndex(item => item.product.id === product.id);

        if (existingIndex >= 0) {
            const updatedItems = [...currentItems];
            updatedItems[existingIndex] = {
                ...updatedItems[existingIndex],
                quantity: updatedItems[existingIndex].quantity + quantity
            };
            this.itemsSignal.set(updatedItems);
        } else {
            this.itemsSignal.set([...currentItems, { product, quantity }]);
        }

        this.saveToStorage();
        this.openCart();
    }

    removeItem(productId: string): void {
        const filtered = this.itemsSignal().filter(item => item.product.id !== productId);
        this.itemsSignal.set(filtered);
        this.saveToStorage();
    }

    updateQuantity(productId: string, quantity: number): void {
        if (quantity <= 0) {
            this.removeItem(productId);
            return;
        }

        const updatedItems = this.itemsSignal().map(item =>
            item.product.id === productId ? { ...item, quantity } : item
        );
        this.itemsSignal.set(updatedItems);
        this.saveToStorage();
    }

    clearCart(): void {
        this.itemsSignal.set([]);
        this.saveToStorage();
    }

    openCart(): void {
        this.isOpenSignal.set(true);
    }

    closeCart(): void {
        this.isOpenSignal.set(false);
    }

    toggleCart(): void {
        this.isOpenSignal.update(isOpen => !isOpen);
    }
}
