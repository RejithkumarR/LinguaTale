import 'package:flutter/material.dart';

void main() {
  runApp(const LinguaTaleApp());
}

class LinguaTaleApp extends StatelessWidget {
  const LinguaTaleApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'LinguaTale',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('LinguaTale')),
      body: const Center(
        child: Text('Write it once. Hear it everywhere.'),
      ),
    );
  }
}
