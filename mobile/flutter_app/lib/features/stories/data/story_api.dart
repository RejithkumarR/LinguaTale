import 'package:dio/dio.dart';
import '../../../core/network/api_client.dart';
import '../domain/entities/story.dart';

class StoryApi {
  final ApiClient client;
  StoryApi(this.client);
  Future<List<Story>> list() async { final r = await client.dio.get('/api/v1/stories'); return (r.data as List).map((e) => Story.fromJson(e)).toList(); }
  Future<Story> create(String title, String content, String sourceLanguage) async { final r = await client.dio.post('/api/v1/stories', data: {'title': title, 'content': content, 'sourceLanguage': sourceLanguage}); return Story.fromJson(r.data); }
  Future<Story> get(int id) async { final r = await client.dio.get('/api/v1/stories/$id'); return Story.fromJson(r.data); }
  Future<Story> update(int id, String title, String content, String sourceLanguage) async { final r = await client.dio.put('/api/v1/stories/$id', data: {'title': title, 'content': content, 'sourceLanguage': sourceLanguage}); return Story.fromJson(r.data); }
  Future<void> delete(int id) async { await client.dio.delete('/api/v1/stories/$id'); }
  Future<Response<dynamic>> generate(int storyId, String language, {String voice = 'alloy'}) => client.dio.post('/api/v1/jobs/generation', data: {'storyId': storyId, 'targetLanguage': language, 'voice': voice});
}