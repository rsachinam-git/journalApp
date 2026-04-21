package com.sbr.journalapp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
@Data
public class JournalEntry {

    //since the @Id will overwrite the mongodb object _id we can skip below or we can write @ObjectId
    private ObjectId id;

    private String title;

    private String content;

}
