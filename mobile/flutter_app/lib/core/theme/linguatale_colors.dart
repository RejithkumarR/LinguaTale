import 'package:flutter/material.dart';

/// LinguaTale brand palette from the approved visual identity.
abstract final class LinguaTaleColors {
  static const primaryBlue = Color(0xFF1E88E5);
  static const teal = Color(0xFF00C2A8);
  static const amber = Color(0xFFFFB300);
  static const coral = Color(0xFFFF6F61);
  static const purple = Color(0xFF8E44AD);
  static const green = Color(0xFF43A047);
  static const pink = Color(0xFFF9A8D4);
  static const deepNavy = Color(0xFF0B2A4A);
  static const surface = Color(0xFFF8FBFF);
  static const white = Color(0xFFFFFFFF);
}

abstract final class LinguaTaleTheme {
  static ThemeData light() {
    final scheme = ColorScheme.fromSeed(
      seedColor: LinguaTaleColors.primaryBlue,
      brightness: Brightness.light,
    ).copyWith(
      primary: LinguaTaleColors.primaryBlue,
      secondary: LinguaTaleColors.teal,
      tertiary: LinguaTaleColors.amber,
      surface: LinguaTaleColors.surface,
      onPrimary: LinguaTaleColors.white,
      onSecondary: LinguaTaleColors.white,
      onSurface: LinguaTaleColors.deepNavy,
    );

    return ThemeData(
      useMaterial3: true,
      colorScheme: scheme,
      scaffoldBackgroundColor: LinguaTaleColors.surface,
      fontFamily: 'Poppins',
      appBarTheme: const AppBarTheme(
        backgroundColor: LinguaTaleColors.white,
        foregroundColor: LinguaTaleColors.deepNavy,
        elevation: 0,
      ),
      cardTheme: CardThemeData(
        color: LinguaTaleColors.white,
        elevation: 1,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(20),
        ),
      ),
      inputDecorationTheme: InputDecorationTheme(
        filled: true,
        fillColor: LinguaTaleColors.white,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(16),
          borderSide: BorderSide.none,
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(16),
          borderSide: const BorderSide(
            color: LinguaTaleColors.primaryBlue,
            width: 2,
          ),
        ),
      ),
    );
  }
}
