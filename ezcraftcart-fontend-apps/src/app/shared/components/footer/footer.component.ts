import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <footer class="bg-foreground text-primary-foreground mt-16">
      <!-- Main Footer -->
      <div class="container-custom py-12">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <!-- Brand -->
          <div class="space-y-4">
            <div class="flex items-center gap-2">
              <img src="logo.svg" alt="EzCraftCart" class="h-10 w-auto brightness-0 invert" />
            </div>
            <p class="text-sm text-primary-foreground/70 leading-relaxed">
              Discover unique handcrafted goods from skilled artisans around the world. 
              Every piece tells a story.
            </p>
            <div class="flex gap-4">
              <a href="#" class="hover:text-accent transition-colors" aria-label="Facebook">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M22 12c0-5.523-4.477-10-10-10S2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.988C18.343 21.128 22 16.991 22 12z"/>
                </svg>
              </a>
              <a href="#" class="hover:text-accent transition-colors" aria-label="Instagram">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zM12 0C8.741 0 8.333.014 7.053.072 2.695.272.273 2.69.073 7.052.014 8.333 0 8.741 0 12c0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98C8.333 23.986 8.741 24 12 24c3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98C15.668.014 15.259 0 12 0zm0 5.838a6.162 6.162 0 100 12.324 6.162 6.162 0 000-12.324zM12 16a4 4 0 110-8 4 4 0 010 8zm6.406-11.845a1.44 1.44 0 100 2.881 1.44 1.44 0 000-2.881z"/>
                </svg>
              </a>
              <a href="#" class="hover:text-accent transition-colors" aria-label="Twitter">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M23.953 4.57a10 10 0 01-2.825.775 4.958 4.958 0 002.163-2.723c-.951.555-2.005.959-3.127 1.184a4.92 4.92 0 00-8.384 4.482C7.69 8.095 4.067 6.13 1.64 3.162a4.822 4.822 0 00-.666 2.475c0 1.71.87 3.213 2.188 4.096a4.904 4.904 0 01-2.228-.616v.06a4.923 4.923 0 003.946 4.827 4.996 4.996 0 01-2.212.085 4.936 4.936 0 004.604 3.417 9.867 9.867 0 01-6.102 2.105c-.39 0-.779-.023-1.17-.067a13.995 13.995 0 007.557 2.209c9.053 0 13.998-7.496 13.998-13.985 0-.21 0-.42-.015-.63A9.935 9.935 0 0024 4.59z"/>
                </svg>
              </a>
              <a href="#" class="hover:text-accent transition-colors" aria-label="Pinterest">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 0C5.373 0 0 5.372 0 12c0 5.084 3.163 9.426 7.627 11.174-.105-.949-.2-2.405.042-3.441.218-.937 1.407-5.965 1.407-5.965s-.359-.719-.359-1.782c0-1.668.967-2.914 2.171-2.914 1.023 0 1.518.769 1.518 1.69 0 1.029-.655 2.568-.994 3.995-.283 1.194.599 2.169 1.777 2.169 2.133 0 3.772-2.249 3.772-5.495 0-2.873-2.064-4.882-5.012-4.882-3.414 0-5.418 2.561-5.418 5.207 0 1.031.397 2.138.893 2.738a.36.36 0 01.083.345l-.333 1.36c-.053.22-.174.267-.402.161-1.499-.698-2.436-2.889-2.436-4.649 0-3.785 2.75-7.262 7.929-7.262 4.163 0 7.398 2.967 7.398 6.931 0 4.136-2.607 7.464-6.227 7.464-1.216 0-2.359-.631-2.75-1.378l-.748 2.853c-.271 1.043-1.002 2.35-1.492 3.146C9.57 23.812 10.763 24 12 24c6.627 0 12-5.373 12-12 0-6.628-5.373-12-12-12z"/>
                </svg>
              </a>
            </div>
          </div>

          <!-- Quick Links -->
          <div>
            <h3 class="font-display text-lg font-semibold mb-4">Quick Links</h3>
            <ul class="space-y-2">
              <li><a routerLink="/" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Home</a></li>
              <li><a routerLink="/products" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">All Products</a></li>
              <li><a href="#" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">New Arrivals</a></li>
              <li><a href="#" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Best Sellers</a></li>
              <li><a href="#" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Sale</a></li>
            </ul>
          </div>

          <!-- Categories -->
          <div>
            <h3 class="font-display text-lg font-semibold mb-4">Categories</h3>
            <ul class="space-y-2">
              <li><a routerLink="/products" [queryParams]="{category: 'ceramic'}" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Ceramic Crafts</a></li>
              <li><a routerLink="/products" [queryParams]="{category: 'wooden'}" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Wooden Crafts</a></li>
              <li><a routerLink="/products" [queryParams]="{category: 'earth'}" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Earth Crafts</a></li>
              <li><a routerLink="/products" [queryParams]="{category: 'scented-oils'}" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Scented Oils</a></li>
              <li><a routerLink="/products" [queryParams]="{category: 'metal'}" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">Metal Crafts</a></li>
            </ul>
          </div>

          <!-- Contact -->
          <div>
            <h3 class="font-display text-lg font-semibold mb-4">Contact Us</h3>
            <ul class="space-y-3">
              <li class="flex items-start gap-3">
                <svg class="w-5 h-5 mt-0.5 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
                <span class="text-sm text-primary-foreground/70">123 Artisan Lane, Craftsville, CA 90210</span>
              </li>
              <li class="flex items-center gap-3">
                <svg class="w-5 h-5 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
                <a href="mailto:hello@ezcraftcart.com" class="text-sm text-primary-foreground/70 hover:text-accent transition-colors">hello&#64;ezcraftcart.com</a>
              </li>
              <li class="flex items-center gap-3">
                <svg class="w-5 h-5 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
                <span class="text-sm text-primary-foreground/70">+1 (555) 123-4567</span>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- Bottom Bar -->
      <div class="border-t border-primary-foreground/10">
        <div class="container-custom py-6">
          <div class="flex flex-col md:flex-row justify-between items-center gap-4">
            <p class="text-sm text-primary-foreground/60">
              © {{ currentYear }} EzCraftCart. All rights reserved.
            </p>
            <div class="flex gap-6">
              <a href="#" class="text-sm text-primary-foreground/60 hover:text-accent transition-colors">Privacy Policy</a>
              <a href="#" class="text-sm text-primary-foreground/60 hover:text-accent transition-colors">Terms of Service</a>
              <a href="#" class="text-sm text-primary-foreground/60 hover:text-accent transition-colors">Shipping Info</a>
            </div>
          </div>
        </div>
      </div>
    </footer>
  `,
  styles: ``
})
export class FooterComponent {
  currentYear = new Date().getFullYear();
}
