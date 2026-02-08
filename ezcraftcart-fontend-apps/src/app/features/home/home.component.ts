import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeroCarouselComponent } from './components/hero-carousel/hero-carousel.component';
import { FeaturedCategoriesComponent } from './components/featured-categories/featured-categories.component';
import { FeaturedProductsComponent } from './components/featured-products/featured-products.component';
import { TrendingProductsComponent } from './components/trending-products/trending-products.component';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        CommonModule,
        HeroCarouselComponent,
        FeaturedCategoriesComponent,
        FeaturedProductsComponent,
        TrendingProductsComponent
    ],
    template: `
    <main>
      <!-- Hero Carousel -->
      <app-hero-carousel />
      
      <!-- Featured Categories -->
      <app-featured-categories />
      
      <!-- Featured Products -->
      <app-featured-products />
      
      <!-- Trending Products -->
      <app-trending-products />

      <!-- Newsletter Section -->
      <section class="py-16 bg-primary">
        <div class="container-custom">
          <div class="max-w-2xl mx-auto text-center">
            <h2 class="font-display text-3xl md:text-4xl font-bold text-primary-foreground">
              Join Our Artisan Community
            </h2>
            <p class="text-primary-foreground/80 mt-4">
              Subscribe to receive exclusive offers, artisan stories, and early access to new collections.
            </p>
            
            <form class="mt-8 flex flex-col sm:flex-row gap-4 max-w-md mx-auto">
              <input 
                type="email" 
                placeholder="Enter your email"
                class="flex-1 input bg-primary-foreground/10 border-primary-foreground/20 text-primary-foreground placeholder:text-primary-foreground/50"
              />
              <button type="submit" class="btn bg-accent text-accent-foreground hover:bg-accent/90 px-8 py-3 whitespace-nowrap">
                Subscribe
              </button>
            </form>
            
            <p class="text-sm text-primary-foreground/60 mt-4">
              By subscribing, you agree to our Privacy Policy and consent to receive updates.
            </p>
          </div>
        </div>
      </section>

      <!-- Trust Badges -->
      <section class="py-12 border-t border-border">
        <div class="container-custom">
          <div class="grid grid-cols-2 md:grid-cols-4 gap-8">
            <div class="text-center">
              <div class="w-12 h-12 mx-auto bg-secondary/10 rounded-full flex items-center justify-center mb-3">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-secondary" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                </svg>
              </div>
              <h3 class="font-medium text-foreground">Authenticity Guaranteed</h3>
              <p class="text-sm text-muted-foreground mt-1">Verified artisan products</p>
            </div>
            
            <div class="text-center">
              <div class="w-12 h-12 mx-auto bg-secondary/10 rounded-full flex items-center justify-center mb-3">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-secondary" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path d="M9 17a2 2 0 11-4 0 2 2 0 014 0zM19 17a2 2 0 11-4 0 2 2 0 014 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16V6a1 1 0 00-1-1H4a1 1 0 00-1 1v10a1 1 0 001 1h1m8-1a1 1 0 01-1 1H9m4-1V8a1 1 0 011-1h2.586a1 1 0 01.707.293l3.414 3.414a1 1 0 01.293.707V16a1 1 0 01-1 1h-1m-6-1a1 1 0 001 1h1M5 17a2 2 0 104 0m-4 0a2 2 0 114 0m6 0a2 2 0 104 0m-4 0a2 2 0 114 0" />
                </svg>
              </div>
              <h3 class="font-medium text-foreground">Free Shipping</h3>
              <p class="text-sm text-muted-foreground mt-1">On orders over $75</p>
            </div>
            
            <div class="text-center">
              <div class="w-12 h-12 mx-auto bg-secondary/10 rounded-full flex items-center justify-center mb-3">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-secondary" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
              </div>
              <h3 class="font-medium text-foreground">Easy Returns</h3>
              <p class="text-sm text-muted-foreground mt-1">30-day return policy</p>
            </div>
            
            <div class="text-center">
              <div class="w-12 h-12 mx-auto bg-secondary/10 rounded-full flex items-center justify-center mb-3">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-secondary" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18.364 5.636l-3.536 3.536m0 5.656l3.536 3.536M9.172 9.172L5.636 5.636m3.536 9.192l-3.536 3.536M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-5 0a4 4 0 11-8 0 4 4 0 018 0z" />
                </svg>
              </div>
              <h3 class="font-medium text-foreground">24/7 Support</h3>
              <p class="text-sm text-muted-foreground mt-1">Here to help anytime</p>
            </div>
          </div>
        </div>
      </section>
    </main>
  `,
    styles: ``
})
export class HomeComponent { }
