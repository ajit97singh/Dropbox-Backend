# Dropbox-Like CRUD Application Backend

This project is a backend implementation for a Dropbox-like cloud storage application. It provides basic CRUD functionalities for managing files and directories, storing files in AWS S3, and using MongoDB to manage metadata. Additionally, it supports file and directory sharing at multiple levels among users, making it an ideal foundation for a collaborative file storage solution.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup and Installation](#setup-and-installation)
- [Environment Variables](#environment-variables)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)

## Features

- **CRUD Operations**: Create, read, update, and delete files and directories.
- **AWS S3 Integration**: Store files in AWS S3, utilizing its scalability and durability.
- **MongoDB for Metadata**: Keep track of file and directory metadata, including names, timestamps, user ownership, and permissions.
- **File and Directory Sharing**: Share files or entire directories with specific users at various permission levels (view, edit, etc.).

## Tech Stack

- **Java , SpringBoot (choose based on your implementation)**
- **MongoDB** for metadata storage
- **AWS S3** for file storage
- **REST API** for handling client requests

## Setup and Installation

 **Clone the Repository**

   ```bash
   git clone https://github.com/ajit97singh/Dropbox-Backend.git
   cd Dropbox-Backend
   ```

## Environment Variables
- MONGODB_URI=your_mongodb_uri
- AWS_ACCESS_KEY_ID=your_aws_access_key
- AWS_SECRET_ACCESS_KEY=your_aws_secret_key
- AWS_S3_BUCKET_NAME=your_s3_bucket_name


## Future Enhancements
- Activity Logging: Track file and directory activities such as uploads, downloads, and edits.
- Version Control: Store previous versions of files.
- Collaborative Editing: Allow multiple users to collaborate on the same file.
- WebSocket Integration: Enable real-time notifications for file sharing and collaboration.
- User Authentication: Secure API endpoints with user authentication for access control.
- User Roles & Permissions: Implement roles such as admin, editor, and viewer.

## Contributing
I welcome contributions! Please follow these steps:
- Fork the project.
- Create your feature branch (git checkout -b feature/YourFeature).
- Commit your changes (git commit -m 'Add YourFeature').
- Push to the branch (git push origin feature/YourFeature).
- Open a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
