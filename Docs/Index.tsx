import { Header } from "@/components/layout/Header";
import { Footer } from "@/components/layout/Footer";
import { CartDrawer } from "@/components/cart/CartDrawer";
import { HeroCarousel } from "@/components/home/HeroCarousel";
import { FeaturedCategories } from "@/components/home/FeaturedCategories";
import { TrendingProducts } from "@/components/home/TrendingProducts";
import { FeaturedProducts } from "@/components/home/FeaturedProducts";
import { Truck, Shield, Headphones, Leaf } from "lucide-react";

const Index = () => {
  return (
    <div className="flex min-h-screen flex-col">
      <Header />
      <CartDrawer />

      <main className="flex-1">
        {/* Hero Carousel */}
        <HeroCarousel />

        {/* Value Propositions */}
        <section className="border-b bg-card py-8">
          <div className="container">
            <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
              <div className="flex items-center gap-4">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary/10">
                  <Truck className="h-6 w-6 text-primary" />
                </div>
                <div>
                  <h3 className="font-medium">Free Shipping</h3>
                  <p className="text-sm text-muted-foreground">On orders over $100</p>
                </div>
              </div>
              <div className="flex items-center gap-4">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-secondary/10">
                  <Shield className="h-6 w-6 text-secondary" />
                </div>
                <div>
                  <h3 className="font-medium">Secure Payment</h3>
                  <p className="text-sm text-muted-foreground">100% protected checkout</p>
                </div>
              </div>
              <div className="flex items-center gap-4">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-accent/20">
                  <Headphones className="h-6 w-6 text-accent-foreground" />
                </div>
                <div>
                  <h3 className="font-medium">24/7 Support</h3>
                  <p className="text-sm text-muted-foreground">Always here to help</p>
                </div>
              </div>
              <div className="flex items-center gap-4">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-artisan-sage/30">
                  <Leaf className="h-6 w-6 text-secondary" />
                </div>
                <div>
                  <h3 className="font-medium">Eco-Friendly</h3>
                  <p className="text-sm text-muted-foreground">Sustainable packaging</p>
                </div>
              </div>
            </div>
          </div>
        </section>

        {/* Featured Categories */}
        <FeaturedCategories />

        {/* Featured Products */}
        <FeaturedProducts />

        {/* Trending Products */}
        <TrendingProducts />

        {/* Artisan Story Banner */}
        <section className="artisan-gradient py-16 md:py-20">
          <div className="container">
            <div className="mx-auto max-w-3xl text-center">
              <h2 className="font-serif text-3xl font-semibold md:text-4xl">
                Every Purchase Supports an Artisan
              </h2>
              <p className="mt-4 text-lg text-muted-foreground">
                When you shop with us, you're not just buying a product—you're supporting 
                skilled craftspeople, preserving traditional techniques, and celebrating 
                the beauty of handmade goods from around the world.
              </p>
              <div className="mt-8 flex flex-wrap justify-center gap-8">
                <div className="text-center">
                  <p className="font-serif text-4xl font-bold text-primary">500+</p>
                  <p className="mt-1 text-sm text-muted-foreground">Artisan Partners</p>
                </div>
                <div className="text-center">
                  <p className="font-serif text-4xl font-bold text-secondary">35</p>
                  <p className="mt-1 text-sm text-muted-foreground">Countries</p>
                </div>
                <div className="text-center">
                  <p className="font-serif text-4xl font-bold text-accent-foreground">50K+</p>
                  <p className="mt-1 text-sm text-muted-foreground">Happy Customers</p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
};

export default Index;
