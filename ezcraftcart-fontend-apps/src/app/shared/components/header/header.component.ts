import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CartService } from '../../../core/services/cart.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  template: `
    <header class="sticky top-0 z-50 w-full bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/80 border-b border-border">
      <div class="container-custom">
        <div class="flex h-16 items-center justify-between">
          <!-- Logo -->
          <a routerLink="/" class="flex items-center gap-2">
            <img src="logo.svg" alt="EzCraftCart" class="h-10 w-auto" />
          </a>

          <!-- Desktop Navigation -->
          <nav class="hidden md:flex items-center gap-8">
            <a routerLink="/" 
               routerLinkActive="text-primary" 
               [routerLinkActiveOptions]="{exact: true}"
               class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
              Home
            </a>
            <a routerLink="/products" 
               routerLinkActive="text-primary"
               class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
              Products
            </a>
            <a href="#categories" 
               class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
              Categories
            </a>
            <a href="#about" 
               class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
              About
            </a>
          </nav>

          <!-- Right Actions -->
          <div class="flex items-center gap-4">
            <!-- Search -->
            <button class="p-2 hover:bg-muted rounded-full transition-colors" aria-label="Search">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </button>

            <!-- Cart Button -->
            <button 
              (click)="cartService.toggleCart()"
              class="relative p-2 hover:bg-muted rounded-full transition-colors"
              aria-label="Shopping Cart">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
              </svg>
              @if (cartService.totalItems() > 0) {
                <span class="absolute -top-1 -right-1 w-5 h-5 bg-primary text-primary-foreground text-xs font-bold rounded-full flex items-center justify-center">
                  {{ cartService.totalItems() }}
                </span>
              }
            </button>

            <!-- Login Button (Desktop) -->
            <a routerLink="/login" 
               class="hidden sm:inline-flex btn-outline text-sm py-2 px-4">
              Sign In
            </a>

            <!-- Mobile Menu Button -->
            <button 
              (click)="toggleMobileMenu()"
              class="md:hidden p-2 hover:bg-muted rounded-full transition-colors"
              aria-label="Toggle Menu">
              @if (isMobileMenuOpen) {
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              } @else {
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
                </svg>
              }
            </button>
          </div>
        </div>

        <!-- Mobile Navigation -->
        @if (isMobileMenuOpen) {
          <nav class="md:hidden py-4 border-t border-border animate-fade-in">
            <div class="flex flex-col gap-4">
              <a routerLink="/" 
                 (click)="closeMobileMenu()"
                 class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
                Home
              </a>
              <a routerLink="/products" 
                 (click)="closeMobileMenu()"
                 class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
                Products
              </a>
              <a href="#categories" 
                 (click)="closeMobileMenu()"
                 class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
                Categories
              </a>
              <a href="#about" 
                 (click)="closeMobileMenu()"
                 class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">
                About
              </a>
              <a routerLink="/login" 
                 (click)="closeMobileMenu()"
                 class="btn-primary text-sm w-full text-center">
                Sign In
              </a>
            </div>
          </nav>
        }
      </div>
    </header>
  `,
  styles: ``
})
export class HeaderComponent {
  cartService = inject(CartService);
  isMobileMenuOpen = false;

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  closeMobileMenu(): void {
    this.isMobileMenuOpen = false;
  }
}
