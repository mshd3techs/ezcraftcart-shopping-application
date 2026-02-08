/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{html,ts}",
    ],
    theme: {
        extend: {
            colors: {
                // Pan-Asian Heritage Palette
                'artisan': {
                    'terracotta': 'hsl(8, 55%, 42%)',
                    'forest': 'hsl(160, 25%, 38%)',
                    'gold': 'hsl(38, 50%, 52%)',
                    'cream': 'hsl(45, 30%, 97%)',
                    'charcoal': 'hsl(30, 25%, 15%)',
                    'sand': 'hsl(40, 22%, 82%)',
                    'sage': 'hsl(145, 18%, 62%)',
                },
                'primary': {
                    DEFAULT: 'hsl(8, 55%, 42%)',
                    'foreground': 'hsl(45, 30%, 97%)',
                },
                'secondary': {
                    DEFAULT: 'hsl(160, 25%, 38%)',
                    'foreground': 'hsl(45, 30%, 97%)',
                },
                'accent': {
                    DEFAULT: 'hsl(38, 50%, 52%)',
                    'foreground': 'hsl(30, 25%, 15%)',
                },
                'muted': {
                    DEFAULT: 'hsl(40, 20%, 92%)',
                    'foreground': 'hsl(35, 15%, 42%)',
                },
                'background': 'hsl(45, 30%, 97%)',
                'foreground': 'hsl(30, 25%, 15%)',
                'card': {
                    DEFAULT: 'hsl(42, 35%, 98%)',
                    'foreground': 'hsl(30, 25%, 15%)',
                },
                'border': 'hsl(35, 18%, 85%)',
                'input': 'hsl(35, 18%, 85%)',
                'ring': 'hsl(8, 55%, 42%)',
                'destructive': {
                    DEFAULT: 'hsl(0, 55%, 48%)',
                    'foreground': 'hsl(45, 30%, 97%)',
                },
            },
            fontFamily: {
                'display': ['Playfair Display', 'serif'],
                'sans': ['Inter', 'sans-serif'],
            },
            borderRadius: {
                'lg': '0.5rem',
                'md': '0.375rem',
                'sm': '0.25rem',
            },
            animation: {
                'fade-in': 'fadeIn 0.5s ease-out',
                'slide-up': 'slideUp 0.5s ease-out',
                'slide-in-right': 'slideInRight 0.3s ease-out',
                'pulse-slow': 'pulse 3s infinite',
            },
            keyframes: {
                fadeIn: {
                    '0%': { opacity: '0' },
                    '100%': { opacity: '1' },
                },
                slideUp: {
                    '0%': { opacity: '0', transform: 'translateY(20px)' },
                    '100%': { opacity: '1', transform: 'translateY(0)' },
                },
                slideInRight: {
                    '0%': { opacity: '0', transform: 'translateX(100%)' },
                    '100%': { opacity: '1', transform: 'translateX(0)' },
                },
            },
        },
    },
    plugins: [],
}
