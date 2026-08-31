class Story {
  final int id;
  final String title;
  final String content;
  final String sourceLanguage;
  final DateTime createdAt;
  final DateTime updatedAt;
  const Story({required this.id, required this.title, required this.content, required this.sourceLanguage, required this.createdAt, required this.updatedAt});
  factory Story.fromJson(Map<String, dynamic> json) => Story(id: json['id'], title: json['title'], content: json['content'], sourceLanguage: json['sourceLanguage'], createdAt: DateTime.parse(json['createdAt']), updatedAt: DateTime.parse(json['updatedAt']));
}