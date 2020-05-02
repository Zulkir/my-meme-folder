export default class DummyDataProvider{
    constructor() {
        this.allUserData = this.generateAllUserData();
    }

    getMyFolderPageData(userId) {
        return this.allUserData[userId];
    }

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