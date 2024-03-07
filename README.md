# AI-BOT

## Introduction
This is a Spring Boot app that usees Spring AI backed by OpenAI's
ChatGPT LLM. This answers question that that is fed to it via text file.
The application stores the Vector store in PGvector, which is a Postgres based
Vector store.

## Local Setup

### Setting up the Persistent Vector Store

Run the below command to create a Postgres DB using a persistant volume
`docker run -d --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e PGDATA=/var/lib/postgresql/data/pgdata -v /Users/dexter/IdeaProjects/aiBot/pgvector:/var/lib/postgresql/data ankane/pgvector`

Run the below command in the Docker Bash mode to query to the DB
`psql -U postgres -h localhost -p 5432`

Query thr vector store inspect if it is already present.
`select count(*) from vector_store;`

Execute the below structure to initiate the Database if it does not exist

`CREATE EXTENSION IF NOT EXISTS vector;`
`CREATE EXTENSION IF NOT EXISTS hstore;`
`CREATE EXTENSION IF NOT EXISTS "uuid-ossp";`

`CREATE TABLE IF NOT EXISTS vector_store (
id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
content text,
metadata json,
embedding vector(1536)
);`
`CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);`