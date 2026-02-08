import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [CommonModule, RouterLink, FormsModule],
    template: `
    <main class="min-h-screen flex items-center justify-center py-12 px-4 artisan-gradient">
      <div class="w-full max-w-md">
        <!-- Logo -->
        <div class="text-center mb-8">
          <a routerLink="/" class="inline-flex items-center gap-2">
            <div class="w-12 h-12 rounded-full bg-primary flex items-center justify-center">
              <span class="text-primary-foreground font-display font-bold text-xl">E</span>
            </div>
            <span class="font-display text-2xl font-semibold text-foreground">EzCraftCart</span>
          </a>
        </div>

        <!-- Register Card -->
        <div class="card p-8">
          <div class="text-center mb-8">
            <h1 class="font-display text-2xl font-bold text-foreground">Create Account</h1>
            <p class="text-muted-foreground mt-2">Join our artisan community today</p>
          </div>

          <form (ngSubmit)="onSubmit()" class="space-y-5">
            <!-- Name -->
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label for="firstName" class="block text-sm font-medium text-foreground mb-2">First Name</label>
                <input 
                  type="text" 
                  id="firstName"
                  name="firstName"
                  [(ngModel)]="firstName"
                  required
                  class="input"
                  placeholder="John"
                />
              </div>
              <div>
                <label for="lastName" class="block text-sm font-medium text-foreground mb-2">Last Name</label>
                <input 
                  type="text" 
                  id="lastName"
                  name="lastName"
                  [(ngModel)]="lastName"
                  required
                  class="input"
                  placeholder="Doe"
                />
              </div>
            </div>

            <!-- Email -->
            <div>
              <label for="email" class="block text-sm font-medium text-foreground mb-2">Email Address</label>
              <input 
                type="email" 
                id="email"
                name="email"
                [(ngModel)]="email"
                required
                class="input"
                placeholder="you@example.com"
              />
            </div>

            <!-- Password -->
            <div>
              <label for="password" class="block text-sm font-medium text-foreground mb-2">Password</label>
              <div class="relative">
                <input 
                  [type]="showPassword ? 'text' : 'password'" 
                  id="password"
                  name="password"
                  [(ngModel)]="password"
                  required
                  class="input pr-10"
                  placeholder="Create a password"
                />
                <button 
                  type="button"
                  (click)="showPassword = !showPassword"
                  class="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground">
                  @if (showPassword) {
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
                    </svg>
                  } @else {
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                    </svg>
                  }
                </button>
              </div>
              <p class="text-xs text-muted-foreground mt-1">Must be at least 8 characters</p>
            </div>

            <!-- Confirm Password -->
            <div>
              <label for="confirmPassword" class="block text-sm font-medium text-foreground mb-2">Confirm Password</label>
              <input 
                type="password" 
                id="confirmPassword"
                name="confirmPassword"
                [(ngModel)]="confirmPassword"
                required
                class="input"
                placeholder="Confirm your password"
              />
            </div>

            <!-- Terms -->
            <div class="flex items-start">
              <input 
                type="checkbox" 
                id="terms"
                name="terms"
                [(ngModel)]="acceptTerms"
                required
                class="w-4 h-4 text-primary rounded border-border mt-0.5"
              />
              <label for="terms" class="ml-2 text-sm text-muted-foreground">
                I agree to the <a href="#" class="text-primary hover:underline">Terms of Service</a> 
                and <a href="#" class="text-primary hover:underline">Privacy Policy</a>
              </label>
            </div>

            <!-- Submit Button -->
            <button type="submit" class="btn-primary w-full">
              Create Account
            </button>

            <!-- Divider -->
            <div class="relative">
              <div class="absolute inset-0 flex items-center">
                <div class="w-full border-t border-border"></div>
              </div>
              <div class="relative flex justify-center text-sm">
                <span class="px-4 bg-card text-muted-foreground">Or sign up with</span>
              </div>
            </div>

            <!-- Social Login -->
            <div class="grid grid-cols-2 gap-4">
              <button type="button" class="btn-ghost border border-border">
                <svg class="w-5 h-5 mr-2" viewBox="0 0 24 24">
                  <path fill="currentColor" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                  <path fill="currentColor" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                  <path fill="currentColor" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                  <path fill="currentColor" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                </svg>
                Google
              </button>
              <button type="button" class="btn-ghost border border-border">
                <svg class="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"/>
                </svg>
                GitHub
              </button>
            </div>
          </form>

          <!-- Sign In Link -->
          <p class="text-center text-sm text-muted-foreground mt-6">
            Already have an account? 
            <a routerLink="/login" class="text-primary font-medium hover:underline">Sign in</a>
          </p>
        </div>
      </div>
    </main>
  `,
    styles: ``
})
export class RegisterComponent {
    firstName = '';
    lastName = '';
    email = '';
    password = '';
    confirmPassword = '';
    acceptTerms = false;
    showPassword = false;

    onSubmit(): void {
        console.log('Registration attempt:', {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email
        });
        // TODO: Implement actual registration
    }
}
