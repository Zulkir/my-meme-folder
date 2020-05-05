import logo192 from "./logo192.png"

export default class DummyDataProvider{
    constructor() {
        this.allUserData = this.generateAllUserData();
    }

    // public
    getMyFolderPageData(userId) {
        return this.allUserData[userId];
    }

    getImageList(user, folderPath) {
        if (user === '1') {
            const items = [];
            for (let i = 0; i < 20; i++) {
                items.push({
                    title: "Meme " + i,
                    thumbnailSrc: logo192
                });
            }
            return items;
        }
        throw new Error("User not found");
    }

    // private
    generateAllUserData() {
        return {
            '1': {
                id: '1',
                name: 'Yuuza',
                folders: this.generateYuuzaFolders()
            }
        };
    }

    generateYuuzaFolders() {
        const folders = [
            {
                name: 'Anime Memes',
                children: [
                    {
                        name: 'K-On',
                        children: []
                    },
                    {
                        name: 'Madoka',
                        children: []
                    }
                ]
            },
            {
                name: 'Game Memes',
                children: [
                    {
                        name: 'Sekiro',
                        children: []
                    },
                    {
                        name: 'Automata',
                        children: []
                    }
                ]
            }
        ];

        function generateIds(nodes) {
            for (let i = 0; i < nodes.length; i++) {
                nodes[i].id = i;
            }
        }

        generateIds(folders);

        return folders;
    }
}