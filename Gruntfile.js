module.exports = function(grunt) {
  grunt.initConfig({
      fileDefs: {
          bowerJsFiles: [
              'stomp-websocket/lib/stomp.min.js',
              'sockjs-client/dist/sockjs.js'
          ],
          cssminFiles: [
              'src/main/webapp/resources/css/main.css'
          ],
          browserifyFiles: [
              'src/main/webapp/resources/scripts/main.js',
              'src/main/webapp/resources/scripts/cardData.js',
              'src/main/webapp/resources/scripts/webSocketService.js',
              'src/main/webapp/resources/scripts/controllers/*.js',
              'src/main/webapp/resources/scripts/logout/logout.js',
              'src/main/webapp/resources/scripts/router/router.js',
              'src/main/webapp/resources/scripts/chat/chat.js'
          ]
      },

      watch: {
          scripts: {
              files: '<%= fileDefs.browserifyFiles %>',
              tasks: ['browserify']
          }
      },

    bowercopy: {
        libs: {
            files: {
                'target/flexpoker/resources/js/libs/': '<%= fileDefs.bowerJsFiles %>',
                'src/main/webapp/resources/js/libs/': '<%= fileDefs.bowerJsFiles %>'
            }
        }
    },

      cssmin: {
        options: {
          shorthandCompacting: false,
          roundingPrecision: -1
        },
        target: {
          files: {
            'src/main/webapp/resources/css/dist/bundle.css': '<%= fileDefs.cssminFiles %>',
            'target/flexpoker/resources/css/dist/bundle.css': '<%= fileDefs.cssminFiles %>'
          }
        }
      },

    browserify: {
      dist: {
        files: {
          'src/main/webapp/resources/js/libs/bundle.js': '<%= fileDefs.browserifyFiles %>',
          'target/flexpoker/resources/js/libs/bundle.js': '<%= fileDefs.browserifyFiles %>'
        },
        options: {
          transform: ['babelify']
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-bowercopy');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.registerTask('default', ['bowercopy', 'cssmin', 'browserify']);
};
