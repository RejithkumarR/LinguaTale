import 'package:flutter_test/flutter_test.dart';
import 'package:linguatale/main.dart';

void main() {
  testWidgets('LinguaTale home renders', (tester) async {
    await tester.pumpWidget(const LinguaTaleApp());

    expect(find.text('LinguaTale'), findsOneWidget);
    expect(find.text('Write it once. Hear it everywhere.'), findsOneWidget);
  });
}
