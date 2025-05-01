from graphviz import Digraph

dot = Digraph(comment='Soundex FST')

# States
dot.node('S', 'Start')
dot.node('K', 'Keep First Letter')
dot.node('D', 'Drop Vowels')
dot.node('M', 'Map to Numbers')
dot.node('C', 'Collapse Digits')
dot.node('P', 'Pad/Trim')
dot.node('E', 'End')

# Transitions
dot.edge('S', 'K', 'input name')
dot.edge('K', 'D', 'keep first, rest')
dot.edge('D', 'M', 'drop vowels')
dot.edge('M', 'C', 'map to numbers')
dot.edge('C', 'P', 'collapse digits')
dot.edge('P', 'E', 'pad/trim to 4')

# Render to file
dot.render('soundex_fst.gv', view=True)