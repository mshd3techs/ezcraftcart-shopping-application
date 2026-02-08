import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-not-found',
    standalone: true,
    imports: [CommonModule, RouterLink],
    template: `
    <main class="min-h-screen flex items-center justify-center py-12 px-4 artisan-pattern">
      <div class="text-center max-w-lg">
        <!-- 404 Illustration -->
        <div class="mb-8">
          <div class="inline-block relative">
            <span class="text-[150px] md:text-[200px] font-display font-bold text-primary/10">404</span>
            <div class="absolute inset-0 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-24 w-24 text-primary" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
          </div>
        </div>

        <h1 class="font-display text-3xl md:text-4xl font-bold text-foreground mb-4">
          Page Not Found
        </h1>
        <p class="text-muted-foreground text-lg mb-8 leading-relaxed">
          Oops! It seems the artisan treasure you're looking for has wandered off. 
          Let's get you back on track.
        </p>

        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <a routerLink="/" class="btn-primary">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
            </svg>
            Go Home
          </a>
          <a routerLink="/products" class="btn-outline">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
            </svg>
            Browse Products
          </a>
        </div>
      </div>
    </main>
  `,
    styles: ``
})
export class NotFoundComponent { }
